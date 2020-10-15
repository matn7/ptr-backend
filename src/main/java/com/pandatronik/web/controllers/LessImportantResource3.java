package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity3;
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
@RequestMapping("${api.version}/users/{username}/lessimportant/3")
public class LessImportantResource3 extends Resource<LessImportantEntity3> {

    public LessImportantResource3(ImportantCrudService<LessImportantEntity3, Long> taskService, UserService userService, MessageSource messageSource) {
        super(taskService, userService, messageSource);
    }


    @PostMapping("")
    public ResponseEntity<LessImportantEntity3> save(@PathVariable("username") String username,
                                                     @Valid @RequestBody LessImportantEntity3 lessImportantEntity3){

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        lessImportantEntity3.setUserEntity(userEntity);

        LessImportantEntity3 newLessImportantRecord = taskService.save(lessImportantEntity3);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLessImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newLessImportantRecord);

    }

    @PutMapping("/{id}")
    public ResponseEntity<LessImportantEntity3> update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody LessImportantEntity3 lessImportantEntity3) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        LessImportantEntity3 newLessImportantRecord = taskService.update(id, lessImportantEntity3);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLessImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newLessImportantRecord);

    }
}
