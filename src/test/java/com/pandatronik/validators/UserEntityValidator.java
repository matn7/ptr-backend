package com.pandatronik.validators;

import com.pandatronik.backend.persistence.domain.UserEntity;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.assertj.core.api.Assertions.assertThat;

public class UserEntityValidator {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void shouldValidateCorrectUserEntity() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(0);
    }

    @Test
    public void shouldValidateToLongUsername() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withUsername(leftPad("b", 51)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("size must be between 6 and 50");
    }

    @Test
    public void shouldValidateToShortUsername() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withUsername(leftPad("b", 5)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("size must be between 6 and 50");
    }

    @Test
    public void shouldValidateBlankUsername() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withUsername(leftPad(" ", 7)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank");
    }

    @Test
    public void shouldValidateNullUsername() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withUsername(null).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(2);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank", "must not be null");
    }

    @Test
    public void shouldValidateToLongPassword() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withPassword(leftPad("b", 25)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("size must be between 6 and 24");
    }

    @Test
    public void shouldValidateToShortPassword() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withPassword(leftPad("b", 5)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("size must be between 6 and 24");
    }

    @Test
    public void shouldValidateBlankPassword() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withPassword(leftPad(" ", 12)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank");
    }

    @Test
    public void shouldValidateNullPassword() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withPassword(null).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(2);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank", "must not be null");
    }

    @Test
    public void shouldValidateInCorrectEmail() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withEmail("pandatronikpanda.pl").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("This not an valid email address");
    }

    @Test
    public void shouldValidateInCorrectEmail_2() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withEmail("@panda.pl").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("This not an valid email address");
    }

    @Test
    @Ignore("fix this")
    public void shouldValidateInCorrectEmail_3() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withEmail("panda@panda").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("This not an valid email address");
    }

    @Test
    public void shouldValidateNullEmail() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withEmail(null).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("must not be null");
    }

    @Test
    public void shouldValidateToLongFirstName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withFirstName(leftPad("b", 51)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("size must be between 0 and 50");
    }

    @Test
    public void shouldValidateNullFirstName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withFirstName(null).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(2);
        assertThat(collect).containsExactlyInAnyOrder("must not be null", "must not be blank");
    }

    @Test
    public void shouldValidateBlankFirstName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withFirstName("   ").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank");
    }

    @Test
    public void shouldValidateToLongLastName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withLastName(leftPad("b", 51)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("size must be between 0 and 50");
    }

    @Test
    public void shouldValidateNullLastName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withLastName(null).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(2);
        assertThat(collect).containsExactlyInAnyOrder("must not be null", "must not be blank");
    }

    @Test
    public void shouldValidateBlankLastName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withLastName("   ").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank");
    }
}
