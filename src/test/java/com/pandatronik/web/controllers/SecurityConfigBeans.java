package com.pandatronik.web.controllers;

import com.pandatronik.backend.service.user.account.CustomUserDetailsService;
import com.pandatronik.backend.service.user.account.EmailService;
import com.pandatronik.backend.service.user.account.PlanService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.security.JwtAuthenticationEntryPoint;
import com.pandatronik.security.JwtTokenProvider;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityConfigBeans {

    @MockBean
    protected UserService userService;

    @MockBean
    private PlanService planService;

    @MockBean
    private EmailService emailService;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    protected Authentication authentication;

    @MockBean
    protected SecurityContextHolder securityContextHolder;

    @MockBean
    protected SecurityContext securityContext;

}
