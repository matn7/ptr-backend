package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.ImportantCrudService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.exceptions.UserNotFoundException;
import com.pandatronik.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public abstract class Resource<T> {

    protected final ImportantCrudService<T,Long> taskService;
    protected final UserService userService;
    private final MessageSource messageSource;


    @GetMapping("/{id}")
    public T findById(@PathVariable("username") String username,
                                      @PathVariable("id") Long id) {
        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        T importantById = taskService.findById(userEntity, id);

//        if (importantById.isPresent()) {
            return taskService.findById(userEntity, id);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
//                            , LocaleContextHolder.getLocale())));
//        }
    }

    @GetMapping("/{year}/{month}/{day}")
    public T findByDate(@PathVariable("username") String username,
            @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);
//
//        Optional<?> importantByData = taskService.findByDate(userEntity, year, month, day);
//
//        if (importantByData.isPresent()) {
            return taskService.findByDate(userEntity, year, month, day);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
//                            , LocaleContextHolder.getLocale())));
//        }
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
