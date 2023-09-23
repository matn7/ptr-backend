package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
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
public class LessImportant2Controller extends Resource<LessImportant2DTO> {

    public LessImportant2Controller(LessImportant2Service taskService, UserService userService) {
        super(taskService, userService);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessImportant2DTO save(@PathVariable("username") String username,
            @RequestBody LessImportant2DTO lessImportant2DTO){

        UserEntity userEntity = userService.findByUserName(username);

        lessImportant2DTO.setUserEntity(userEntity);

        return taskService.save(lessImportant2DTO);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LessImportant2DTO update(@PathVariable("username") String username, @PathVariable("id") Long id,
                                    @Valid @RequestBody LessImportant2DTO lessImportant2DTO) {

        UserEntity userEntity = userService.findByUserName(username);
        lessImportant2DTO.setUserEntity(userEntity);
        return taskService.update(id, lessImportant2DTO);
    }
}



























