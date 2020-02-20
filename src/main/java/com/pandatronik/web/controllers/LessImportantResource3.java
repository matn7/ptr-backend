package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity3;
import com.pandatronik.backend.service.ImportantCrudService;
import com.pandatronik.backend.service.LessImportantService3;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.exceptions.UserNotFoundException;
import com.pandatronik.utils.HeaderUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

        LessImportantEntity3 newLessImportantRecord = taskService.update(userEntity, id, lessImportantEntity3);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLessImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newLessImportantRecord);

    }
}
