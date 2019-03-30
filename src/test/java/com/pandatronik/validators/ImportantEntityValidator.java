package com.pandatronik.validators;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
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
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

public class ImportantEntityValidator {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void shouldValidateCorrectImportantEntity() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(0);
    }


    @Test
    public void shouldValidateToLongTitle() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().withTitle(leftPad("a", 41)).build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("size must be between 1 and 40");
    }

    @Test
    public void shouldValidateBlankTitle() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().withTitle("  ").build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertEquals(collect.get(0), "must not be blank");
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank");
    }

    @Test
    public void shouldValidateNullTitle() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().withTitle(null).build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(2);
        assertThat(collect).containsExactlyInAnyOrder("must not be null", "must not be blank");
    }

    @Test
    public void shouldValidateToLongBody() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().withBody(leftPad("a", 256)).build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("size must be between 1 and 255");
    }

    @Test
    public void shouldValidateNullBody() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().withBody(null).build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(2);
        assertThat(collect).containsExactlyInAnyOrder("must not be null", "must not be blank");
    }

    @Test
    public void shouldValidateBlankBody() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().withBody("  ").build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(2);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank", "");
    }

    @Test
    public void shouldValidateNullMade() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().withMade(null).build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(2);
        assertThat(collect).containsExactlyInAnyOrder("must not be null", "must not be blank");
    }

    @Test
    public void shouldValidateNullPostedOn() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().withPostedOn(null).build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("must not be null");
    }

    @Test
    public void shouldValidateNullStartDate() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().withStartDate(null).build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("must not be null");
    }

    @Test
    public void shouldValidateNullUserProfileId() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().withUserProfileId(null).build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(2);
        assertThat(collect).containsExactlyInAnyOrder("must not be null", "must not be blank");
    }

    @Test
    @Ignore("Fix when make sure upid must have 30 chars")
    public void shouldValidateToLongUserProfileId() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().withUserProfileId(leftPad("a", 31)).build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("size must be between 30 and 30");
    }

    @Test
    @Ignore
    public void shouldValidateToShortUserProfileId() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().withUserProfileId(leftPad("a", 29)).build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("size must be between 30 and 30");
    }

    @Test
    public void shouldValidateBlankUserProfileId() {
        ImportantEntity entity = ImportantEntityProvider.getValidImportantEntity().withUserProfileId(leftPad(" ", 30)).build();

        Set<ConstraintViolation<ImportantEntity>> violations = validator.validate(entity);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());
        assertThat(collect).hasSize(1);
        assertThat(collect).containsExactlyInAnyOrder("must not be blank");
    }

    // todo test somehow that for one data cannot be two record for the same user
}
