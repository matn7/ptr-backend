package com.pandatronik.web.controllers.users;

import com.pandatronik.backend.persistence.domain.*;
import com.pandatronik.backend.service.user.account.*;
import com.pandatronik.enums.PlansEnum;
import com.pandatronik.enums.RolesEnum;
import com.pandatronik.enums.TokenEnum;
import com.pandatronik.enums.TokenNotFoundEnum;
import com.pandatronik.utils.HeaderUtil;
import com.pandatronik.utils.UserUtils;
import com.pandatronik.web.controllers.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Validated
@RestController
@RequestMapping("${api.version}")
@CrossOrigin(origins = "${angular.api.url}")
public class SignupResource {

    private static final Logger LOG = LoggerFactory.getLogger(SignupResource.class);

    public static final String ACTIVATE_ACCOUNT_PATH = "/activateaccount";

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private EmailService emailService;

    @Value("${webmaster.email}")
    private String webMasterEmail;

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;

    @Autowired
    private UserValidationService userValidationService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private Validator validator;

    public SignupResource()
    {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserEntity user, HttpServletRequest request, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);

        if (errorMap != null) {
            return errorMap;
        }

        ResponseEntity<?> validate = userValidationService.validate(user);

        if (validate != null) {
            return validate;
        }

        // Set plan
        Plan selectedPlan = planService.findPlanById(PlansEnum.BASIC.getId());
        user.setPlan(selectedPlan);

        Set<UserRole> roles = new HashSet<>();
        roles.add(new UserRole(user, new Role(RolesEnum.BASIC)));

//        user.setEnabled(true);

        UserEntity newUser = userService.createUser(user, PlansEnum.BASIC, roles);

        if (newUser != null) {
            TokenEntity activateToken = tokenService.createPasswordResetTokenForEmail(newUser.getEmail());
            if (null == activateToken) {
                LOG.info("Token {}", activateToken.getToken());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorMessage("Record not found"));
            } else {
                LOG.info("Token {}", activateToken.getToken());
                UserEntity userEntity = activateToken.getUser();
                String token = activateToken.getToken();

                String activateAccountUrl = UserUtils.createActivateUserUrl(request, userEntity.getId(), token);

                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(userEntity.getEmail());
                mailMessage.setSubject("Pandatronik: activate account");
                mailMessage.setText("Please click on the link below to activate account " + activateAccountUrl);
                mailMessage.setFrom(webMasterEmail);

                emailService.sendGenericEmailMessage(mailMessage);

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ErrorMessage("User created. Password sent to entered email"));
            }
        }


        return new ResponseEntity<UserEntity>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/activatenewuser")
    public ResponseEntity<?> changeUserPassword(@RequestParam("id") long id,
                                                @RequestParam("token") String token) {
        if (StringUtils.isEmpty(token) || id == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Invalid token or id"));
        }

        TokenEntity activateToken = tokenService.findByToken(token);

        if (null == activateToken) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(TokenNotFoundEnum.TOKEN_NOT_FOUND.getMessage()));
        }

        UserEntity user = activateToken.getUser();

        if (user.getId() != id) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User id not match"));
        }

        if (LocalDateTime.now(Clock.systemUTC()).isAfter(activateToken.getExpiryDate())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(TokenEnum.TOKEN_EXPIRED.getMessage()));
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

        userService.activateUser(id);
        LOG.info("User for id {} successfully activated {}", id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new CustomMessage("Outstanding user activated enjoy :)"));
    }

    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<Void> deleteImportant(@PathVariable("username") String username,
                                                @PathVariable("id") Long id) {
        userService.deleteUser(username, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> readUserByUsername(@PathVariable("username") String username) {
        UserEntity userByUsername = userService.findByUserName(username);

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse(userByUsername.getEmail(),
                userByUsername.getFirstName(), userByUsername.getLastName(),
                userByUsername.getUsername(), userByUsername.isEnabled(), userByUsername.getPlan(),
                userByUsername.getUserRoles());

        if (userByUsername != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userDetailsResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

}
