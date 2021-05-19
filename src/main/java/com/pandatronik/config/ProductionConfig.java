package com.pandatronik.config;

import com.pandatronik.backend.service.user.account.EmailService;
import com.pandatronik.backend.service.user.account.SmtpEmailService;
import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
//@PropertySource("file:///${user.home}/.pandatronik_properties/application-pandatronik-rest-prod.properties")
public class ProductionConfig {

//    @Value("${stripe.test.private.key}")
//    private String stripeDevKey;

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }

    @Bean
    public ServletRegistrationBean h2ConsoleServletRegistration() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new WebServlet());
        bean.addUrlMappings("/console/*");
        return bean;
    }

//    @Bean
//    public String stripeKey() {
//        return stripeDevKey;
//    }
}
