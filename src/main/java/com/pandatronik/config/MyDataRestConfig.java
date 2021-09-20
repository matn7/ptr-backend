package com.pandatronik.config;

import com.pandatronik.backend.persistence.domain.Plan;
import com.pandatronik.backend.persistence.domain.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedRestConfigurations = {HttpMethod.PUT, HttpMethod.POST,
                HttpMethod.DELETE, HttpMethod.PATCH};

        // disable HTTP methods for Product: PUT, POST and DELETE
        disableHttpMethods(Role.class, config, theUnsupportedRestConfigurations);

        // disable HTTP methods for Product: PUT, POST and DELETE
        disableHttpMethods(Plan.class, config, theUnsupportedRestConfigurations);

    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config,
                                    HttpMethod[] theUnsupportedRestConfigurations) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedRestConfigurations)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(theUnsupportedRestConfigurations)));
    }

}
