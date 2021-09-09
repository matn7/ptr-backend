package com.pandatronik.validator;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import com.pandatronik.backend.service.ImportantService;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImportantEntityUniqueValidator implements ConstraintValidator<ImportantEntityUnique, ImportantDTO> {

    @Autowired
    private ImportantService importantService;

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(ImportantDTO importantDTO, ConstraintValidatorContext constraintValidatorContext) {
        int year = importantDTO.getStartDate().getYear();
        int month = importantDTO.getStartDate().getMonthValue();
        int day = importantDTO.getStartDate().getDayOfMonth();

        final String name = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userService.findByUserName(name);

        // duplicate check, only for new entry, allow update
        if (importantService.duplicateCheck(userEntity, year, month, day) != null
                && importantDTO.getId() == null) {
            return false;
        }

        return true;
    }
}
