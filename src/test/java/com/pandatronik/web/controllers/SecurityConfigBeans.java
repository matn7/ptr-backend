package com.pandatronik.web.controllers;

import com.pandatronik.backend.service.user.account.CustomUserDetailsService;
import com.pandatronik.backend.service.user.account.EmailService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.security.JwtAuthenticationEntryPoint;
import com.pandatronik.security.JwtTokenProvider;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityConfigBeans {

    @MockBean
    UserService userService;

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

}
