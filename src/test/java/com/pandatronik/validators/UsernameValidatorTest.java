package com.pandatronik.validators;

import com.pandatronik.backend.persistence.domain.Plan;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.UserRole;
import com.pandatronik.configuration.ValidatorConfiguration;
import com.pandatronik.enums.PlansEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pandatronik.utils.MessagesConstants.*;
import static com.pandatronik.utils.ValidCredentials.VALID_EMAIL;
import static com.pandatronik.utils.ValidCredentials.VALID_PASSWORD;
import static com.pandatronik.utils.ValidCredentials.VALID_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
    classes = {ValidatorConfiguration.class})
public class UsernameValidatorTest {

    @Autowired
    private Validator validator;

    @Test
    public void testValidUsername() {
        UserEntity userEntity = getUserEntity(VALID_USERNAME);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidUsername() {
        UserEntity userEntity = getUserEntity("username");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder(INVALID_USERNAME_MSG);
    }

    @Test
    public void testUsernameStartsWithPanda() {
        UserEntity userEntity = getUserEntity("panda");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(3);
        assertThat(collect).containsExactlyInAnyOrder(SIZE_6_50_MSG,
                USERNAME_PANDA_MSG, INVALID_USERNAME_MSG);
    }

    private UserEntity getUserEntity(String username) {

        Set<UserRole> roles = new HashSet<>();

        UserEntity userEntity = UserEntity.builder()
                    .username(username)
                    .password(VALID_PASSWORD)
                    .email(VALID_EMAIL)
                    .firstName("Panda")
                    .lastName("Panda")
                    .plan(new Plan(PlansEnum.BASIC))
                    .build();

        userEntity.setUserRoles(roles);

        return userEntity;
    }

}
