package com.pandatronik.validator;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.LessImportantDTO;
import com.pandatronik.backend.service.LessImportantService;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LessImportantEntityUniqueValidator implements ConstraintValidator<LessImportantEntityUnique, LessImportantDTO> {

    @Autowired
    private LessImportantService lessImportantService;

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(LessImportantDTO lessImportantDTO, ConstraintValidatorContext constraintValidatorContext) {
        int year = lessImportantDTO.getStartDate().getYear();
        int month = lessImportantDTO.getStartDate().getMonthValue();
        int day = lessImportantDTO.getStartDate().getDayOfMonth();

        final String name = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userService.findByUserName(name);

        if (lessImportantService.duplicateCheck(userEntity, year, month, day) != null
                && lessImportantDTO.getId() == null) {
            return false;
        }

        return true;
    }
}
