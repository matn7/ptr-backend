package com.pandatronik.validator;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.service.ImportantService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ImportantEntityUniqueValidator implements ConstraintValidator<ImportantEntityUnique, ImportantEntity> {

    @Autowired
    private ImportantService importantService;

    @Override
    public boolean isValid(ImportantEntity importantEntity, ConstraintValidatorContext constraintValidatorContext) {
        Optional<ImportantEntity> isRecordExists = importantService.getImportantByUidYearMonthDay(importantEntity.getUserProfileId(),
                importantEntity.getStartDate().getYear(), importantEntity.getStartDate().getMonthValue(),
                importantEntity.getStartDate().getDayOfMonth());
        return isRecordExists == null;
    }
}
