package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.service.Important2Service;
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
@RequestMapping(AppConstants.BASE_URL + "/{username}/important/2")
public class Important2Controller extends Resource<Important2DTO> {

    public Important2Controller(Important2Service importantService, UserService userService) {
        super(importantService, userService);
    }

    @Override
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Important2DTO save(@PathVariable("username") String username,
            @Valid @RequestBody Important2DTO important2DTO){

        UserEntity userEntity = userService.findByUserName(username);

//        important2DTO.setUserEntity(userEntity);

        return taskService.save(important2DTO);
    }

    @Override
    @PutMapping("/{id}")
    public Important2DTO update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody Important2DTO important2DTO) {

        UserEntity userEntity = userService.findByUserName(username);
//        important2DTO.setUserEntity(userEntity);

        return taskService.update(id, important2DTO);
    }

}



























