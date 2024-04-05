package com.pandatronik.validators;

import com.pandatronik.backend.persistence.domain.Plan;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.UserRole;
import com.pandatronik.enums.PlansEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pandatronik.utils.ValidCredentials.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsernameValidatorTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
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
        assertThat(collect).containsExactlyInAnyOrder("Username is reserved word");
    }

    @Test
    public void testUsernameStartsWithPanda() {
        UserEntity userEntity = getUserEntity("panda");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(3);
        assertThat(collect).containsExactlyInAnyOrder("Username is reserved word",
                "Username size must be between 6 and 20", "Username cannot starts with panda");
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
