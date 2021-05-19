package com.pandatronik.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.profile.ProfileCredentialsProvider;
//import com.amazonaws.regions.Region;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.s3.AmazonS3Client;
//@Configuration
//@EnableJpaRepositories(basePackages = "com.pandatronik.backend.persistence.repositories")
//@EntityScan(basePackages = "com.pandatronik.backend.persistence.domain")
//@PropertySource("file:///${user.home}/.pandatronik/application-common.properties")
//@PropertySource("file:///${user.home}/.pandatronik/stripe.properties")
//@EnableTransactionManagement
@Configuration
@PropertySource("file:///${user.home}/.pandatronik/application-common.properties")
public class ApplicationConfig {

//    @Value("${aws.s3.profile}")
//    private String awsProfileName;

//    @Bean
//    public AmazonS3Client s3Client() {
//        AWSCredentials credentials = new ProfileCredentialsProvider(awsProfileName).getCredentials();
//        AmazonS3Client s3Client = new AmazonS3Client(credentials);
//        Region region = Region.getRegion(Regions.US_WEST_2);
//        s3Client.setRegion(region);
//        return s3Client;
//    }

}
