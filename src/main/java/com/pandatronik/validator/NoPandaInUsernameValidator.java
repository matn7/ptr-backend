package com.pandatronik.validator;

import com.pandatronik.backend.persistence.domain.UserEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class NoPandaInUsernameValidator implements ConstraintValidator<NoPandaInUsernameConstraint, UserEntity> {
    @Override
    public boolean isValid(UserEntity userEntity, ConstraintValidatorContext constraintValidatorContext) {

        String lowercaseUsername = userEntity.getUsername().toLowerCase();
        boolean startsWithPanda = StringUtils.startsWithAny(lowercaseUsername, new String[]{"panda"});
        return !startsWithPanda;
    }
}
