package com.pandatronik.validator;

import com.pandatronik.backend.persistence.domain.UserEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, UserEntity> {

    static List<String> invalidUsernames = new ArrayList<>();

    static {
        invalidUsernames.add("panda");
        invalidUsernames.add("panda2");
        invalidUsernames.add("username");
        invalidUsernames.add("password");
        invalidUsernames.add("pandatronik");
    }


    @Override
    public boolean isValid(UserEntity userEntity, ConstraintValidatorContext constraintValidatorContext) {
        return !invalidUsernames.contains(userEntity.getUsername());
    }
}
