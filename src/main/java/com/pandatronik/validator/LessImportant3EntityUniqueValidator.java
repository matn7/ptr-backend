package com.pandatronik.validator;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.LessImportant3DTO;
import com.pandatronik.backend.service.LessImportant3Service;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LessImportant3EntityUniqueValidator implements ConstraintValidator<LessImportant3EntityUnique, LessImportant3DTO> {

    @Autowired
    private LessImportant3Service lessImportantService;

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(LessImportant3DTO lessImportantDTO, ConstraintValidatorContext constraintValidatorContext) {
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
