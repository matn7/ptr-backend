package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity3;
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
@RequestMapping("${api.version}/users/{username}/important/3")
public class ImportantResource3 extends Resource<ImportantEntity3> {

    public ImportantResource3(ImportantCrudService<ImportantEntity3, Long> importantService, UserService userService, MessageSource messageSource) {
        super(importantService, userService, messageSource);
    }

    @Override
    @PostMapping("")
    public ResponseEntity<ImportantEntity3> save(@PathVariable("username") String username,
            @Valid @RequestBody ImportantEntity3 importantEntity3){

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        importantEntity3.setUserEntity(userEntity);

        ImportantEntity3 newImportantRecord = taskService.save(importantEntity3);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newImportantRecord);

    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ImportantEntity3> update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody ImportantEntity3 importantEntity3) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);
        ImportantEntity3 newImportantRecord = taskService.update(id, importantEntity3);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newImportantRecord);

    }
}



























