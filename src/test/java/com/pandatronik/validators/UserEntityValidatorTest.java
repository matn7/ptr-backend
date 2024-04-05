package com.pandatronik.validators;

import com.pandatronik.backend.persistence.domain.UserEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.assertj.core.api.Assertions.assertThat;

public class UserEntityValidatorTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void shouldValidateCorrectUserEntity() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(0);
    }

    @Test
    public void shouldValidateToLongUsername() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withUsername(leftPad("b", 51)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).containsExactlyInAnyOrder("Username size must be between 6 and 20");
    }

    @Test
    public void shouldValidateToShortUsername() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withUsername(leftPad("b", 5)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("Username size must be between 6 and 20");
    }

    @Test
    public void shouldValidateBlankUsername() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withUsername(leftPad(" ", 7)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank");
    }

    @Test
    public void shouldValidateEmptyUsername() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withUsername("").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(2);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank", "Username size must be between 6 and 20");
    }

    @Test
    public void shouldValidateToLongPassword() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withPassword(leftPad("b", 65)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(2);
        assertThat(collect).containsExactlyInAnyOrder(
                "Password must contain at least one number, one special character, one upper and lower case letter",
                "Password size must be between 6 and 60");
    }

    @Test
    public void shouldValidateToShortPassword() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withPassword(leftPad("b", 5)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).containsExactlyInAnyOrder("Password size must be between 6 and 60",
                "Password must contain at least one number, one special character, one upper and lower case letter");
    }

    @Test
    public void shouldValidateBlankPassword() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withPassword(leftPad(" ", 12)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).containsExactlyInAnyOrder("must not be blank",
                "Password must contain at least one number, one special character, one upper and lower case letter");
    }

    @Test
    public void shouldValidateEmptyPassword() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withPassword("").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(3);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank", "Password size must be between 6 and 60",
                "Password must contain at least one number, one special character, one upper and lower case letter");
    }

    @Test
    public void shouldValidateInCorrectEmail() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withEmail("pandatronikpanda.pl").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("Email address invalid");
    }

    @Test
    public void shouldValidateInCorrectEmail_2() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withEmail("@panda.pl").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("Email address invalid");
    }

    @Test
    @Disabled("fix this")
    public void shouldValidateInCorrectEmail_3() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withEmail("panda@panda").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("This not an valid email address");
    }

    @Test
    public void shouldValidateNullEmail() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withEmail(null).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("must not be null");
    }

    @Test
    public void shouldValidateToLongFirstName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withFirstName(leftPad("b", 51)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("FirstName size must be between 1 and 20");
    }

    @Test
    public void shouldValidateNullFirstName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withFirstName(null).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(2);
        assertThat(collect).containsExactlyInAnyOrder("must not be null", "must not be blank");
    }

    @Test
    public void shouldValidateBlankFirstName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withFirstName("   ").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank");
    }

    @Test
    public void shouldValidateToLongLastName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withLastName(leftPad("b", 51)).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("LastName size must be between 1 and 20");
    }

    @Test
    public void shouldValidateNullLastName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withLastName(null).build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(2);
        assertThat(collect).containsExactlyInAnyOrder("must not be null", "must not be blank");
    }

    @Test
    public void shouldValidateBlankLastName() {
        UserEntity entity = UserEntityProvider.getValidUserEntity().withLastName("   ").build();

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank");
    }
}
