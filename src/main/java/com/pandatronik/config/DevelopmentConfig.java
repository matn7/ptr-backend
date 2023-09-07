package com.pandatronik.config;

import com.pandatronik.backend.service.user.account.EmailService;
import com.pandatronik.backend.service.user.account.MockEmailService;
import com.pandatronik.backend.service.user.account.SmtpEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import org.h2.server.web.WebServlet;

@Configuration
@Profile("dev")
//@PropertySource("file:///${user.home}/.pandatronik_properties/application-pandatronik-rest-dev.properties")
public class DevelopmentConfig {

    private String stripeDevKey;

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }

//    @Bean
//    public ServletRegistrationBean h2ConsoleServletRegistration() {
//        ServletRegistrationBean bean = new ServletRegistrationBean(new WebServlet());
//        bean.addUrlMappings("/console/*");
//        return bean;
//    }

    @Bean
    public String stripeKey() {
        return stripeDevKey;
    }
}
