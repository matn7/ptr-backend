package com.pandatronik.validators;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.CrudService;
import com.pandatronik.web.controllers.SecurityConfigBeans;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public abstract class AbstractTaskValidatorTest<T> extends SecurityConfigBeans {

    @Autowired
    private Validator validator;

    @Test
    public void validateCorrectTaskDTO() {
        UserEntity user = UserEntity.builder().build();
        T task = getTask();

        when(userService.findByUserName(anyString())).thenReturn(user);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(getUser());

        Set<ConstraintViolation<T>> violations = validator.validate(task);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());

        assertEquals(0, collect.size());
    }

    @Test
    public void validateDuplicateTaskDTO() {
        UserEntity user = UserEntity.builder().build();
        T task = getTask();

        when(userService.findByUserName(anyString())).thenReturn(user);
        when(getService().duplicateCheck(anyLong(), anyInt(), anyInt(), anyInt())).thenReturn(task);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(getUser());

        Set<ConstraintViolation<T>> violations = validator.validate(task);

        List<String> collect = violations.stream().map(val -> val.getMessage()).collect(Collectors.toList());

        assertEquals(1, collect.size());
        assertEquals("Entry for specified date already exists", collect.get(0));
    }

    protected abstract T getTask();

    protected abstract CrudService<T, Long> getService();

    protected abstract String getUser();

    protected abstract String body();

    protected abstract String title();
}
