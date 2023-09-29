package com.pandatronik.integration;

import com.pandatronik.backend.persistence.domain.Plan;
import com.pandatronik.backend.persistence.domain.Role;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.UserRole;
import com.pandatronik.backend.persistence.repositories.user.account.PlanRepository;
import com.pandatronik.backend.persistence.repositories.user.account.RoleRepository;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
import com.pandatronik.backend.service.user.account.EmailService;
import com.pandatronik.enums.PlansEnum;
import com.pandatronik.enums.RolesEnum;
import com.pandatronik.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractIntegrationTest {

    @Autowired
    protected PlanRepository planRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected EmailService emailService;

    protected Plan createPlan(PlansEnum plansEnum) {
        return new Plan(plansEnum);
    }

    protected Role createRole(RolesEnum rolesEnum) {
        return new Role(rolesEnum);
    }

    protected UserEntity createUser(String username, String email) {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);

        UserEntity basicUser = UserUtils.createBasicUser(username, email);
        basicUser.setPlan(basicPlan);

        Role basicRole = createRole(RolesEnum.BASIC);
        roleRepository.save(basicRole);

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, basicRole);
        userRoles.add(userRole);

        basicUser.getUserRoles().addAll(userRoles);
        basicUser = userRepository.save(basicUser);
        return basicUser;
    }

    protected UserEntity createUser(String random) {
        return createUser(random, random + "@pandatronik.com");
    }

}
