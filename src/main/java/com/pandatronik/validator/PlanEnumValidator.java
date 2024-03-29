package com.pandatronik.validator;

import com.pandatronik.backend.persistence.domain.UserEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlanEnumValidator implements ConstraintValidator<PlanEnumConstraint, UserEntity> {

    @Override
    public boolean isValid(UserEntity userEntity, ConstraintValidatorContext constraintValidatorContext) {
//        return userEntity.getPlan() == PlansEnum.BASIC || userEntity.getPlan() == PlansEnum.PRO;

        return true;
    }
}
