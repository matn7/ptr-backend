package com.pandatronik.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class ValidatorConfig {

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Bean
    public SpringConstraintValidatorFactory springConstraintValidatorFactory() {
        return new SpringConstraintValidatorFactory(autowireCapableBeanFactory);
    }

    @Bean
    public Validator validator () {

        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure().constraintValidatorFactory(springConstraintValidatorFactory())
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        return validator;
    }

}
