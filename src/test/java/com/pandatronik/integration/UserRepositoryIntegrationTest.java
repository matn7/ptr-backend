package com.pandatronik.integration;

import com.pandatronik.PandatronikRestApplication;
import com.pandatronik.backend.persistence.domain.Plan;
import com.pandatronik.backend.persistence.domain.Role;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.UserRole;
import com.pandatronik.enums.PlansEnum;
import com.pandatronik.enums.RolesEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = PandatronikRestApplication.class)
public class UserRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void testCreateNewPlan() throws Exception {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);
        Plan retrievedPlan = planRepository.findById(PlansEnum.BASIC.getId()).get();
        assertNotNull(retrievedPlan);
    }

    @Test
    public void testCreateNewRole() throws Exception {

        Role userRole  = createRole(RolesEnum.BASIC);
        roleRepository.save(userRole);

        Role retrievedRole = roleRepository.findById(RolesEnum.BASIC.getId()).get();
        assertNotNull(retrievedRole);
    }

    @Test
    public void createNewUser() {

        UserEntity basicUser = createUser("createNewUser", "createNewUser@pandatronik.com");

        UserEntity newlyCreatedUser = userRepository.findById(basicUser.getId()).get();
        assertNotNull(newlyCreatedUser);
        assertEquals(true, newlyCreatedUser.getId() != 0);
        assertNotNull(newlyCreatedUser.getPlan());
        assertNotNull(newlyCreatedUser.getPlan().getId());
        Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.getUserRoles();
        for (UserRole ur : newlyCreatedUserUserRoles) {
            assertNotNull(ur.getRole());
            assertNotNull(ur.getRole().getId());
        }

    }

    @Test
    public void deleteUser() throws Exception {

        String username = "deleteUserTest";
        String email = "deleteUserTest@pandatronik.com";

        UserEntity basicUser = createUser(username, email);
        assertNotNull(userRepository.findById(basicUser.getId()));
        userRepository.deleteById(basicUser.getId());
        assertEquals(Optional.empty(), userRepository.findById(basicUser.getId()));

    }

    @Test
    public void getUserByEmail() throws Exception {
        UserEntity user = createUser("getUserByEmailTest");

        UserEntity newlyFoundUser = userRepository.findByEmail(user.getEmail());
        assertNotNull(newlyFoundUser);
        assertNotNull(newlyFoundUser.getId());
    }

    @Test
    public void updateUserPassword() throws Exception {
        UserEntity user = createUser("updateUserPassword");
        assertNotNull(user);
        assertNotNull(user.getId());

        String newPassword = UUID.randomUUID().toString();

        userRepository.updateUserPassword(user.getId(), newPassword);

        user = userRepository.findById(user.getId()).get();
        assertEquals(newPassword, user.getPassword());

    }


}
