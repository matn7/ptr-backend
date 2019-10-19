package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity2;
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
@AllArgsConstructor
public class ImportantResource2 {

    private final ImportantService2 importantService;
    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("username") String username,
            @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        Optional<ImportantEntity2> importantById = importantService.findById(userEntity, id);

        if (importantById.isPresent()) {
            return ResponseEntity.ok(importantById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<?> findByDate(@PathVariable("username") String username,
            @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        Optional<ImportantEntity2> importantByData = importantService.findByDate(userEntity, year, month, day);

        if (importantByData.isPresent()) {
            return ResponseEntity.ok(importantByData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }

    @PostMapping("")
    public ResponseEntity<ImportantEntity2> save(@PathVariable("username") String username,
            @Valid @RequestBody ImportantEntity2 importantEntity2){

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        importantEntity2.setUserEntity(userEntity);

        ImportantEntity2 newImportantRecord = importantService.save(importantEntity2);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newImportantRecord);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ImportantEntity2> update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody ImportantEntity2 importantEntity2) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        ImportantEntity2 newImportantRecord = importantService.update(userEntity, id, importantEntity2);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newImportantRecord);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("username") String username,
                                       @PathVariable("id") Long id) {
        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        importantService.delete(userEntity, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }

    private void checkUser(UserEntity userEntity) {
        if (isNull(userEntity)) {
            throw new UserNotFoundException(messageSource.getMessage("user.not.found.message", null
                    , LocaleContextHolder.getLocale()));
        }
    }

}



























