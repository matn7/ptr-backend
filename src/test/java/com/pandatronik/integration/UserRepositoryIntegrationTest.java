package com.pandatronik.integration;

import com.pandatronik.backend.persistence.domain.Plan;
import com.pandatronik.backend.persistence.domain.Role;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.UserRole;
import com.pandatronik.enums.PlansEnum;
import com.pandatronik.enums.RolesEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test-mysql.properties")
public class UserRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${sql.script.disable.fk.check}")
    private String disableForeignKeyCheck;

    @Value("${sql.script.delete.userEntity}")
    private String deleteUser;

    @BeforeEach
    public void setup() {
        jdbc.execute(disableForeignKeyCheck);
        jdbc.execute(deleteUser);
    }

    @Test
    public void testCreateNewPlan() {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);
        Optional<Plan> planById = planRepository.findById(PlansEnum.BASIC.getId());
        assertTrue(planById.isPresent());
        Plan retrievedPlan = planById.get();
        assertNotNull(retrievedPlan);
    }

    @Test
    public void testCreateNewRole() {

        Role userRole  = createRole(RolesEnum.BASIC);
        roleRepository.save(userRole);

        Optional<Role> roleById = roleRepository.findById(RolesEnum.BASIC.getId());
        assertTrue(roleById.isPresent());

        Role retrievedRole = roleById.get();
        assertNotNull(retrievedRole);
    }

    @Test
    public void createNewUser() throws Exception {

        String username = RandomStringUtils.randomAlphabetic(10);
        String email = username + "@pandatronik.com";

        UserEntity basicUser = createUser(username, email);

        UserEntity newlyCreatedUser = userRepository.findById(basicUser.getId()).get();
        assertNotNull(newlyCreatedUser);
        assertTrue(newlyCreatedUser.getId() != 0);
        assertNotNull(newlyCreatedUser.getPlan());
        Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.getUserRoles();
        for (UserRole ur : newlyCreatedUserUserRoles) {
            assertNotNull(ur.getRole());
        }

    }

    @Test
    public void testDeleteUser() {

        String username = RandomStringUtils.randomAlphabetic(10);
        String email = username + "@pandatronik.com";

        UserEntity basicUser = createUser(username, email);
        userRepository.deleteById(basicUser.getId());
    }

    @Test
    public void testGetUserByEmail() {
        UserEntity user = createUser("testName");

        UserEntity newlyFoundUser = userRepository.findByEmail(user.getEmail());
        assertNotNull(newlyFoundUser);
    }

    @Test
    public void testUpdateUserPassword() {
        String username = RandomStringUtils.randomAlphabetic(10);
        UserEntity user = createUser(username);
        assertNotNull(user);

        String newPassword = UUID.randomUUID().toString();

        userRepository.updateUserPassword(user.getId(), newPassword);

        Optional<UserEntity> userById = userRepository.findById(user.getId());
        assertTrue(userById.isPresent());
        assertEquals(newPassword, userById.get().getPassword());
    }
}
