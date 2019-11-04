package com.pandatronik.validator;

import com.pandatronik.backend.persistence.domain.UserEntity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, UserEntity> {

    static List<String> invalidUsernames = new ArrayList<>();

    static {
        // todo add this to some text, properties file
        invalidUsernames.add("panda");
        invalidUsernames.add("panda2");
        invalidUsernames.add("username");
        invalidUsernames.add("password");
    }


    @Override
    public boolean isValid(UserEntity userEntity, ConstraintValidatorContext constraintValidatorContext) {

        return !invalidUsernames.contains(userEntity.getUsername());
    }
}
