package com.pandatronik.web.controllers.users;

import com.pandatronik.backend.persistence.domain.PasswordResetToken;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.user.account.EmailService;
import com.pandatronik.backend.service.user.account.PasswordResetTokenService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.payload.EmailRequest;
import com.pandatronik.payload.LoginRequest;
import com.pandatronik.utils.UserUtils;
import com.pandatronik.web.controllers.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;

@RestController
@RequestMapping("${api.version}")
@CrossOrigin(origins = "${angular.api.url}")
public class ForgotMyPasswordResource {

	private static final Logger LOG = LoggerFactory.getLogger(ForgotMyPasswordResource.class);

	public static final String CHANGE_PASSWORD_PATH = "/changeuserpassword";

	@Autowired
	private EmailService emailService;

	@Value("${webmaster.email}")
	private String webMasterEmail;

	@Autowired
	private PasswordResetTokenService passwordResetTokenService;

	@Autowired
	private UserService userService;

	@PostMapping("/forgotpassword")
	public ResponseEntity<?> forgotPassword(
			HttpServletRequest request,
			@Valid @RequestBody EmailRequest emailRequest, BindingResult result) {

		PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetTokenForEmail(emailRequest.getEmail());
		if (null == passwordResetToken) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorMessage("Record not found"));
		} else {
			LOG.info("Token {}", passwordResetToken.getToken());
			UserEntity user = passwordResetToken.getUser();
			String token = passwordResetToken.getToken();

			String resetPasswordUrl = UserUtils.createPasswordResetUrl(request, user.getId(), token);

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("Pandatronik: how to reset password");
			mailMessage.setText("Please click on the link below to reset your password" + " " + resetPasswordUrl);
			mailMessage.setFrom(webMasterEmail);

			emailService.sendGenericEmailMessage(mailMessage);

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ErrorMessage("Password sent to entered email"));
		}
	}

	@PostMapping("/changepassword")
	public ResponseEntity<?> changeUserPassword(@RequestParam("id") long id,
												@RequestParam("token") String token,
												@RequestBody LoginRequest loginRequest) {
		if (StringUtils.isEmpty(token) || id == 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorMessage("Invalid token or id"));
		}

		PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);

		if (null == passwordResetToken) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorMessage("Token not found"));
		}

		UserEntity user = passwordResetToken.getUser();

		if (user.getId() != id) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorMessage("User id not match"));
		}

		if (LocalDateTime.now(Clock.systemUTC()).isAfter(passwordResetToken.getExpiryDate())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorMessage("Token has expired"));
		}

		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null == authentication) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorMessage("You are not authorized to perform this request"));
		}

		UserEntity userEntity = (UserEntity) authentication.getPrincipal();
		if (userEntity.getId() != id) {
			LOG.error("Security breach! User {} is trying to make a password reset request on behalf of {}",
					userEntity.getId(), id);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new ErrorMessage("You are not authorized to perform this request"));
		}

		userService.updateUserPassword(id, loginRequest.getPassword());
		LOG.info("Password successfully updated for user {}", userEntity.getUsername());

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ErrorMessage("Outstanding password changed"));
	}
}