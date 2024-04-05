package com.pandatronik.validator;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.service.ImportantService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ImportantEntityUniqueValidator implements ConstraintValidator<ImportantEntityUnique, ImportantEntity> {

    @Autowired
    private ImportantService importantService;

    @Override
    public boolean isValid(ImportantEntity importantEntity, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
