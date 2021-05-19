package com.pandatronik.integration;

import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;

//import org.junit.rules.TestName;

public class AbstractServiceIntegrationTest {

    @Autowired
    protected UserService userService;

//    protected UserEntity createUser(TestName testName) {
//        String username = testName.getMethodName();
//        String email = testName.getMethodName() + "@pandatronik.com";
//
//        Set<UserRole> userRoles = new HashSet<>();
//
//        UserEntity basicUser = UserUtils.createBasicUser(username, email);
//        userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));
//
//        return userService.createUser(basicUser, PlansEnum.BASIC, userRoles);
//    }


}
