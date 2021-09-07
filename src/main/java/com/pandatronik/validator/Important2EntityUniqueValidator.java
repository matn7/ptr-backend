package com.pandatronik.validator;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.service.Important2Service;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Important2EntityUniqueValidator implements ConstraintValidator<Important2EntityUnique, Important2DTO> {

    @Autowired
    private Important2Service importantService;

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(Important2DTO importantDTO, ConstraintValidatorContext constraintValidatorContext) {
        int year = importantDTO.getStartDate().getYear();
        int month = importantDTO.getStartDate().getMonthValue();
        int day = importantDTO.getStartDate().getDayOfMonth();

        final String name = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userService.findByUserName(name);

        if (importantService.duplicateCheck(userEntity.getId(), year, month, day) != null) {
            return false;
        }

        return true;
    }


}
