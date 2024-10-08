package com.pandatronik;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
public class PandatronikRestApplication {

    @Value("${webmaster.username}")
    private String webmasterUsername;

    @Value("${webmaster.password}")
    private String webmasterPassword;

    @Value("${webmaster.email}")
    private String webmasterEmail;

//    // BUG-1
//    @PostConstruct
//    public void started() {
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//    }

    @Bean
    public RequestContextListener requestContextListener(){
        RequestContextListener requestContextListener = new RequestContextListener();
        return requestContextListener;
    }

//    @Bean
//    @Primary
//    public LocaleResolver localeResolver() {
//        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
//        localeResolver.setDefaultLocale(Locale.US);
//        return localeResolver;
//    }

    public static void main(String[] args) {
        SpringApplication.run(PandatronikRestApplication.class, args);
    }

}
