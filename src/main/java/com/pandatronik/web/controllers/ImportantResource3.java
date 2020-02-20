package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity3;
import com.pandatronik.backend.service.ImportantCrudService;
import com.pandatronik.backend.service.ImportantService3;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.exceptions.UserNotFoundException;
import com.pandatronik.utils.HeaderUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import static java.util.Objects.isNull;

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
        ImportantEntity3 newImportantRecord = taskService.update(userEntity, id, importantEntity3);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newImportantRecord);

    }
}



























