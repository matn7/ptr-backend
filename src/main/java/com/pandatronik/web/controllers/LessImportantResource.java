package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.service.ImportantCrudService;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/lessimportant/1")
public class LessImportantResource extends Resource<LessImportantEntity> {


    public LessImportantResource(ImportantCrudService<LessImportantEntity, Long> taskService, UserService userService, MessageSource messageSource) {
        super(taskService, userService, messageSource);
    }

    @PostMapping("")
    public ResponseEntity<LessImportantEntity> save(@PathVariable("username") String username,
                                                     @Valid @RequestBody LessImportantEntity lessImportantEntity){

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        lessImportantEntity.setUserEntity(userEntity);

        LessImportantEntity newLessImportantRecord = taskService.save(lessImportantEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLessImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newLessImportantRecord);

    }

    @PutMapping("/{id}")
    public ResponseEntity<LessImportantEntity> update(@PathVariable("username") String username,
                                                       @PathVariable("id") Long id,
                                                      @Valid @RequestBody LessImportantEntity lessImportantEntity) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        LessImportantEntity newLessImportantRecord = taskService.update(id, lessImportantEntity);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLessImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newLessImportantRecord);

    }
}



























