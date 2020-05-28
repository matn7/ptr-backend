package com.pandatronik.validator;

import com.pandatronik.backend.persistence.domain.UserEntity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, UserEntity> {
    @Override
    public boolean isValid(UserEntity userEntity, ConstraintValidatorContext constraintValidatorContext) {

        String regex = "^(?=.*?\\p{Lu})(?=.*?\\p{Ll})(?=.*?\\d)(?=.*?[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).*$";

        boolean matches = Pattern.compile(regex).matcher(userEntity.getPassword()).matches();
        return matches;
    }


}
