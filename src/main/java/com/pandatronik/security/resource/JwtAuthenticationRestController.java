package com.pandatronik.security.resource;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.exceptions.AuthenticationException;
import com.pandatronik.security.JwtTokenProvider;
import com.pandatronik.security.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${api.version}/users")
@CrossOrigin(origins = "${angular.api.url}")
public class JwtAuthenticationRestController {

    private String tokenHeader = SecurityConstants.HEADER_STRING;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);

        if (jwtTokenProvider.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenProvider.refreshToken(token);
            return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}
