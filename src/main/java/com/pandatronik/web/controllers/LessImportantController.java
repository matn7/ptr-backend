package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.LessImportantDTO;
import com.pandatronik.backend.service.LessImportantService;
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
@RequestMapping(AppConstants.BASE_URL + "/{username}/lessimportant/1")
public class LessImportantController extends Resource<LessImportantDTO> {

    public LessImportantController(LessImportantService taskService, UserService userService) {
        super(taskService, userService);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessImportantDTO save(@PathVariable("username") String username,
                                 @Valid @RequestBody LessImportantDTO lessImportantDTO){

        UserEntity userEntity = userService.findByUserName(username);
        lessImportantDTO.setUserEntity(userEntity);
        return taskService.save(lessImportantDTO);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LessImportantDTO update(@PathVariable("username") String username, @PathVariable("id") Long id,
                                   @Valid @RequestBody LessImportantDTO lessImportantDTO) {

        UserEntity userEntity = userService.findByUserName(username);
        lessImportantDTO.setUserEntity(userEntity);
        return taskService.update(id, lessImportantDTO);
    }
}



























