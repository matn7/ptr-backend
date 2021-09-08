package com.pandatronik.integration;

import com.pandatronik.backend.persistence.domain.Role;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.UserRole;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.enums.PlansEnum;
import com.pandatronik.enums.RolesEnum;
import com.pandatronik.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashSet;
import java.util.Set;

public class AbstractServiceIntegrationTest {

    @Autowired
    protected UserService userService;

    protected UserEntity createUser() {
        String username = "someuser";
        String email = "someuser@pandatronik.com";

        Set<UserRole> userRoles = new HashSet<>();
        UserEntity basicUser = UserUtils.createBasicUser(username, email);
        userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));

        return userService.createUser(basicUser, PlansEnum.BASIC, userRoles);
    }
}
