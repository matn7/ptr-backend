package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.ImportantCrudService;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@RequiredArgsConstructor
public abstract class Resource<T> {

    protected final ImportantCrudService<T, Long> taskService;
    protected final UserService userService;


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public T findById(@PathVariable("username") String username, @PathVariable("id") Long id) {
        UserEntity userEntity = userService.findByUserName(username);
        return taskService.findById(userEntity, id);
    }

    @GetMapping("/{year}/{month}/{day}")
    @ResponseStatus(HttpStatus.OK)
    public T findByDate(@PathVariable("username") String username,
            @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {

        UserEntity userEntity = userService.findByUserName(username);

        return taskService.findByDate(userEntity, year, month, day);
    }

    public abstract T save(@PathVariable("username") String username,
                                                @Valid @RequestBody T entity);

    public abstract T update(@PathVariable("username") String username,
        @PathVariable("id") Long id, @Valid @RequestBody T entity);


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("username") String username, @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);
        taskService.delete(userEntity, id);
    }

}
