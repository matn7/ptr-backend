package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportant2Entity;
import com.pandatronik.backend.persistence.model.LessImportant2DTO;
import com.pandatronik.backend.service.LessImportant2Service;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/lessimportant/2")
public class LessImportant2Controller extends Resource<LessImportant2DTO, LessImportant2Entity, Long> {

    public LessImportant2Controller(LessImportant2Service taskService) {
        super(taskService);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessImportant2DTO save(@PathVariable("username") String username,
            @Valid @RequestBody LessImportant2DTO lessImportant2DTO){

        return taskService.save(username, lessImportant2DTO);
    }

    @Override
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public LessImportant2DTO update(@PathVariable("username") String username,
                                    @Valid @RequestBody LessImportant2DTO lessImportant2DTO) {
        return taskService.save(username, lessImportant2DTO);
    }
}



























