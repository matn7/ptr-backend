package com.pandatronik.validator;

import com.pandatronik.backend.persistence.domain.UserEntity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<EmailConstraint, UserEntity> {
    @Override
    public boolean isValid(UserEntity userEntity, ConstraintValidatorContext constraintValidatorContext) {

        if (userEntity.getEmail() == null) {
            // Other validators check that
            return false;
        }

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean matches = Pattern.compile(regex).matcher(userEntity.getEmail()).matches();
        return matches;
    }


}
