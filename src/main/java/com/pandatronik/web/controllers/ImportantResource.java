package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.service.ImportantService;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/important/1")
public class ImportantResource extends Resource<ImportantEntity> {

    public ImportantResource(ImportantService importantService, UserService userService, MessageSource messageSource) {
        super(importantService, userService, messageSource);
    }

    @Override
    @PostMapping("")
    public ResponseEntity<ImportantEntity> save(@PathVariable("username") String username,
                                                @Valid @RequestBody ImportantEntity importantEntity) {
        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        importantEntity.setUserEntity(userEntity);

        ImportantEntity newImportantRecord = taskService.save(importantEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newImportantRecord);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ImportantEntity> update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody ImportantEntity importantEntity) {
        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);
        ImportantEntity newImportantRecord = taskService.update(userEntity, id, importantEntity);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newImportantRecord);
    }
}



























