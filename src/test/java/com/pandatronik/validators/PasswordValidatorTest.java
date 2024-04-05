package com.pandatronik.validators;

import com.pandatronik.backend.persistence.domain.Plan;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.UserRole;
import com.pandatronik.configuration.ValidatorConfiguration;
import com.pandatronik.enums.PlansEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pandatronik.utils.MessagesConstants.INVALID_PASSWORD_FORMAT_MSG;
import static com.pandatronik.utils.ValidCredentials.VALID_EMAIL;
import static com.pandatronik.utils.ValidCredentials.VALID_PASSWORD;
import static com.pandatronik.utils.ValidCredentials.VALID_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordValidatorTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testValidPassword() {
        UserEntity userEntity = getUserEntity(VALID_PASSWORD);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidPasswordNoNumbers() {
        UserEntity userEntity = getUserEntity("InvalidP@ssword");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder(INVALID_PASSWORD_FORMAT_MSG);
    }

    @Test
    public void testInvalidPasswordNoSpecialChars() {
        UserEntity userEntity = getUserEntity("Inva1idPassw0rd");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder(INVALID_PASSWORD_FORMAT_MSG);
    }

    @Test
    public void testInvalidPasswordNoUppercase() {
        UserEntity userEntity = getUserEntity("inva1idp@ssw0rd");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder(INVALID_PASSWORD_FORMAT_MSG);
    }


    @Test
    public void testInvalidPasswordNoLowercase() {
        UserEntity userEntity = getUserEntity("INVALIDP@SSW0RD");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder(INVALID_PASSWORD_FORMAT_MSG);
    }

    private UserEntity getUserEntity(String password) {

        Set<UserRole> roles = new HashSet<>();

        UserEntity userEntity = UserEntity.builder()
                .username(VALID_USERNAME)
                .password(password)
                .email(VALID_EMAIL)
                .firstName("Panda")
                .lastName("Panda")
                .plan(new Plan(PlansEnum.BASIC))
                .build();

        userEntity.setUserRoles(roles);

        return userEntity;
    }

}
