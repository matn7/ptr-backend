package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.ImportantCrudService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.exceptions.UserNotFoundException;
import com.pandatronik.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public abstract class Resource<T> {

    protected final ImportantCrudService<T,Long> taskService;
    protected final UserService userService;
    private final MessageSource messageSource;


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("username") String username,
                                      @PathVariable("id") Long id) {
        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        Optional<?> importantById = taskService.findById(userEntity, id);

        if (importantById.isPresent()) {
            return ResponseEntity.ok(importantById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<?> findByDate(@PathVariable("username") String username,
            @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        Optional<?> importantByData = taskService.findByDate(userEntity, year, month, day);

        if (importantByData.isPresent()) {
            return ResponseEntity.ok(importantByData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }

    public abstract ResponseEntity<T> save(@PathVariable("username") String username,
                                                @Valid @RequestBody T entity);

    public abstract ResponseEntity<T> update(@PathVariable("username") String username,
        @PathVariable("id") Long id, @Valid @RequestBody T entity);


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("username") String username,
                                       @PathVariable("id") Long id) {
        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        taskService.delete(userEntity, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }

    protected void checkUser(UserEntity userEntity) {
        if (isNull(userEntity)) {
            throw new UserNotFoundException(messageSource.getMessage("user.not.found.message", null
                    , LocaleContextHolder.getLocale()));
        }
    }
}
