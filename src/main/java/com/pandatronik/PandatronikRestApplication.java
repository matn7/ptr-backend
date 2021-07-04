package com.pandatronik;

import com.pandatronik.backend.service.user.account.PlanService;
import com.pandatronik.backend.service.user.account.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class PandatronikRestApplication {

    private static final Logger LOG = LoggerFactory.getLogger(PandatronikRestApplication.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;

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
    public RequestContextListener requestContextListener(){
        RequestContextListener requestContextListener = new RequestContextListener();
        return requestContextListener;
    }

    public static void main(String[] args) {
        SpringApplication.run(PandatronikRestApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        LOG.info("Creating Basic and Pro plans in the database...");
//
//        planService.createPlan(PlansEnum.BASIC.getId());
//        planService.createPlan(PlansEnum.PRO.getId());
//
//        UserEntity user = UserUtils.createBasicUser(webmasterUsername, webmasterEmail);
//        user.setPassword(webmasterPassword);
//        Set<UserRole> userRoles = new HashSet<>();
//        userRoles.add(new UserRole(user, new Role(RolesEnum.ADMIN)));
//        LOG.debug("Creating user with username {}", user.getUsername());
//        userService.createUser(user, PlansEnum.PRO, userRoles);
//        LOG.info("User {} created", user.getUsername());
//    }
}
