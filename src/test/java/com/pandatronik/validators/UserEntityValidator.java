package com.pandatronik.validators;

import com.pandatronik.backend.persistence.domain.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.pandatronik.utils.MessagesConstants.INVALID_PASSWORD_FORMAT_MSG;
import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserEntityValidator {

    @Autowired
    private Validator validator;

    @Test
    public void validateCorrectUserEntity() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(0, collect.size());
    }

    @Test
    public void validateToLongUsername() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withUsername(leftPad("b", 51)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals("size must be between 5 and 20", collect.get(0));
    }

    @Test
    public void validateToShortUsername() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withUsername(leftPad("b", 4)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals("size must be between 5 and 20", collect.get(0));
    }

    @Test
    public void validateBlankUsername() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withUsername(leftPad(" ", 7)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals("must not be blank", collect.get(0));
    }

    @Test
    public void validateNullUsername() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withUsername(null).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(3, collect.size());
        assertThat(collect).containsExactlyInAnyOrder("must not be blank", "must not be null", "this username is not allowed");
    }

    @Test
    public void validateToLongPassword() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withPassword(leftPad("b", 61)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(2, collect.size());
        assertThat(collect).containsExactlyInAnyOrder(INVALID_PASSWORD_FORMAT_MSG, "size must be between 6 and 60");
    }

    @Test
    public void validateToShortPassword() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withPassword(leftPad("b", 5)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(2, collect.size());
        assertThat(collect).containsExactlyInAnyOrder(INVALID_PASSWORD_FORMAT_MSG, "size must be between 6 and 60");
    }

    @Test
    public void validateBlankPassword() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withPassword(leftPad(" ", 12)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(2, collect.size());
        assertThat(collect).containsExactlyInAnyOrder(INVALID_PASSWORD_FORMAT_MSG, "must not be blank");
    }

    @Test
    public void validateNullPassword() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withPassword(null).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(3);
        assertThat(collect).containsExactlyInAnyOrder(INVALID_PASSWORD_FORMAT_MSG, "must not be blank", "must not be null");
    }

    @Test
    public void validateInvalidEmail() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withEmail("pandatronikpanda.pl").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals("invalid email address", collect.get(0));
    }

    @Test
    public void validateInvalidEmail2() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withEmail("@panda.pl").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals("invalid email address", collect.get(0));
    }

    @Test
    public void validateInvalidEmail3() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withEmail("panda@panda").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals("invalid email address", collect.get(0));
    }

    @Test
    public void validateNullEmail() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withEmail(null).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(2, collect.size());
        assertThat(collect).containsExactlyInAnyOrder("must not be null", "invalid email address");
    }

    @Test
    public void validateToLongFirstName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withFirstName(leftPad("b", 21)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals("size must be between 0 and 20", collect.get(0));
    }

    @Test
    public void validateNullFirstName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withFirstName(null).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(2, collect.size());
        assertThat(collect).containsExactlyInAnyOrder("must not be null", "must not be blank");
    }

    @Test
    public void validateBlankFirstName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withFirstName("   ").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals("must not be blank", collect.get(0));
    }

    @Test
    public void validateToLongLastName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withLastName(leftPad("b", 21)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals("size must be between 0 and 20", collect.get(0));
    }

    @Test
    public void validateNullLastName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withLastName(null).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(2, collect.size());
        assertThat(collect).containsExactlyInAnyOrder("must not be null", "must not be blank");
    }

    @Test
    public void validateBlankLastName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withLastName("   ").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(1, collect.size());
        assertEquals("must not be blank", collect.get(0));
    }
}
