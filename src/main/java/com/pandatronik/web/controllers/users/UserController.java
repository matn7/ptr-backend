package com.pandatronik.web.controllers.users;

import com.pandatronik.backend.service.user.account.MapValidationErrorService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.payload.JWTLoginSuccessResponse;
import com.pandatronik.payload.LoginRequest;
import com.pandatronik.security.JwtTokenProvider;
import com.pandatronik.security.LoginAttemptService;
import com.pandatronik.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.pandatronik.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                              BindingResult result) {

        LOG.info("Execute authentication here");

        String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            System.out.println("HERE");
            return new ResponseEntity<String>("Login from IP " + ip + " was blocked for 3 minutes. Try again later.",
                    HttpStatus.UNAUTHORIZED);
        }


        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        LOG.info("Execute authentication here 2");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        LOG.info("Execute authentication here 3");

       SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));

    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
