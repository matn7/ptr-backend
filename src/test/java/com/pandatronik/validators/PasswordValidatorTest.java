package com.pandatronik.validators;

import com.pandatronik.backend.persistence.domain.Plan;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.UserRole;
import com.pandatronik.enums.PlansEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pandatronik.utils.MessagesConstants.INVALID_PASSWORD_FORMAT_MSG;
import static com.pandatronik.utils.ValidCredentials.VALID_EMAIL;
import static com.pandatronik.utils.ValidCredentials.VALID_PASSWORD;
import static com.pandatronik.utils.ValidCredentials.VALID_USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PasswordValidatorTest {

    @Autowired
    private Validator validator;

    @Test
    public void validPassword() {
        UserEntity userEntity = getUserEntity(VALID_PASSWORD);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        assertEquals(0, violations.size());
    }

    @Test
    public void invalidPasswordNoNumbers() {
        UserEntity userEntity = getUserEntity("InvalidP@ssword");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals(INVALID_PASSWORD_FORMAT_MSG, collect.get(0));
    }

    @Test
    public void invalidPasswordNoSpecialChars() {
        UserEntity userEntity = getUserEntity("Inva1idPassw0rd");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals(INVALID_PASSWORD_FORMAT_MSG, collect.get(0));
    }

    @Test
    public void invalidPasswordNoUppercase() {
        UserEntity userEntity = getUserEntity("inva1idp@ssw0rd");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals(INVALID_PASSWORD_FORMAT_MSG, collect.get(0));
    }

    @Test
    public void invalidPasswordNoLowercase() {
        UserEntity userEntity = getUserEntity("INVALIDP@SSW0RD");

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals(INVALID_PASSWORD_FORMAT_MSG, collect.get(0));
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
