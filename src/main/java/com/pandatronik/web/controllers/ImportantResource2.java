package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity2;
import com.pandatronik.backend.service.ImportantCrudService;
import com.pandatronik.backend.service.ImportantService2;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.exceptions.UserNotFoundException;
import com.pandatronik.utils.HeaderUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import static java.util.Objects.isNull;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/important/2")
public class ImportantResource2 extends Resource<ImportantEntity2> {

    public ImportantResource2(ImportantService2 importantService, UserService userService, MessageSource messageSource) {
        super(importantService, userService, messageSource);
    }

    @Override
    @PostMapping("")
    public ResponseEntity<ImportantEntity2> save(@PathVariable("username") String username,
            @Valid @RequestBody ImportantEntity2 importantEntity2){

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        importantEntity2.setUserEntity(userEntity);

        ImportantEntity2 newImportantRecord = taskService.save(importantEntity2);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newImportantRecord);

    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ImportantEntity2> update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody ImportantEntity2 importantEntity2) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        ImportantEntity2 newImportantRecord = taskService.update(userEntity, id, importantEntity2);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newImportantRecord);

    }

}



























