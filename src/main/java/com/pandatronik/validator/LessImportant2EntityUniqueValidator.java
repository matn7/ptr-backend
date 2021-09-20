package com.pandatronik.validator;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.LessImportant2DTO;
import com.pandatronik.backend.service.LessImportant2Service;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LessImportant2EntityUniqueValidator implements ConstraintValidator<LessImportant2EntityUnique, LessImportant2DTO> {

    @Autowired
    private LessImportant2Service lessImportantService;

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(LessImportant2DTO lessImportantDTO, ConstraintValidatorContext constraintValidatorContext) {
        int year = lessImportantDTO.getStartDate().getYear();
        int month = lessImportantDTO.getStartDate().getMonthValue();
        int day = lessImportantDTO.getStartDate().getDayOfMonth();

        final String name = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userService.findByUserName(name);

        // && lessImportantDTO.getId() == null
        if (lessImportantService.duplicateCheck(userEntity, year, month, day) != null) {
            return false;
        }

        return true;
    }
}
