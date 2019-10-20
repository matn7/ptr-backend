package com.pandatronik;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
public class PandatronikRestApplication { //  implements CommandLineRunner

    private static final Logger LOG = LoggerFactory.getLogger(PandatronikRestApplication.class);

    @Value("${webmaster.username}")
    private String webmasterUsername;

    @Value("${webmaster.password}")
    private String webmasterPassword;

    @Value("${webmaster.email}")
    private String webmasterEmail;

    // BUG-1
    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RequestContextListener requestContextListener(){
        RequestContextListener requestContextListener = new RequestContextListener();
        return requestContextListener;
    }


    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }
//
//    @Bean
//    public MessageSource messageSource() {
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename("classpath:messages");
//        messageSource.setCacheSeconds(10); //reload messages every 10 seconds
//        return messageSource;
//    }

    public static void main(String[] args) {
        SpringApplication.run(PandatronikRestApplication.class, args);
    }

}
