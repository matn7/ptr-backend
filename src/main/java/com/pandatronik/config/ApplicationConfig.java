package com.pandatronik.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.pandatronik.backend.persistence.repositories")
@EntityScan(basePackages = "com.pandatronik.backend.persistence.domain")
// todo: move application-common.properties to docker
//@PropertySource("file:///${user.home}/.pandatronik/application-common.properties")
//@PropertySource("file:///${user.home}/.pandatronik/stripe.properties")
@EnableTransactionManagement
public class ApplicationConfig {


}
