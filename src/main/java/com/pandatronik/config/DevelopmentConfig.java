package com.pandatronik.config;

import com.pandatronik.backend.service.user.account.EmailService;
import com.pandatronik.backend.service.user.account.MockEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevelopmentConfig {

    private String stripeDevKey;

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }

    @Bean
    public String stripeKey() {
        return stripeDevKey;
    }
}
