package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.Important3DTO;
import com.pandatronik.backend.service.Important3Service;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/important/3")
public class Important3Controller extends Resource<Important3DTO> {

    public Important3Controller(Important3Service importantService, UserService userService) {
        super(importantService, userService);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Important3DTO save(@PathVariable("username") String username,
            @Valid @RequestBody Important3DTO important3DTO){

        UserEntity userEntity = userService.findByUserName(username);

        important3DTO.setUserEntity(userEntity);

        return taskService.save(important3DTO);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Important3DTO update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody Important3DTO important3DTO) {

        UserEntity userEntity = userService.findByUserName(username);
        important3DTO.setUserEntity(userEntity);

        return taskService.update(id, important3DTO);
    }
}



























