package com.pandatronik.security;

import com.google.gson.Gson;
import com.pandatronik.enums.TokenEnum;
import com.pandatronik.exceptions.InvalidLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.pandatronik.enums.TokenEnum.TOKEN_EXPIRED;
import static java.util.Objects.nonNull;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        InvalidLoginResponse loginResponse = new InvalidLoginResponse();
        if (nonNull(httpServletResponse.getHeader(TOKEN_EXPIRED.getId()))) {
            loginResponse.setTokenExpired(TOKEN_EXPIRED.getMessage());
        }

        String jsonLoginResponse = new Gson().toJson(loginResponse);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);

        httpServletResponse.getWriter().print(jsonLoginResponse);

        // Max login attempts
        final String xfHeader = httpServletRequest.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            loginAttemptService.loginFailed(httpServletRequest.getRemoteAddr());
        } else {
            loginAttemptService.loginFailed(xfHeader.split(",")[0]);
        }
    }
}
