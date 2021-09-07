package com.pandatronik.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class I18NConfig {

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		// check for new messages every 30 minutes
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1800);
		return messageSource;
	}

}
