package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity2;
import com.pandatronik.backend.service.LessImportantService2;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.utils.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static com.pandatronik.utils.ApplicationUtils.API_VERSION;
import static java.util.Objects.isNull;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/lessimportant/2")
public class LessImportantResource2 {

    private final LessImportantService2 lessImportantService2;

    private final UserService userService;

    @Autowired
    public LessImportantResource2(LessImportantService2 lessImportantService2,
                                  UserService userService) {
        this.lessImportantService2 = lessImportantService2;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("username") String username,
                                      @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User not found"));
        }

        Optional<LessImportantEntity2> lessImportantById = lessImportantService2.findById(userEntity, id);

        if (lessImportantById.isPresent()) {
            return ResponseEntity.ok(lessImportantById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<?> findByDate(@PathVariable("username") String username,
            @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User not found"));
        }

        Optional<LessImportantEntity2> lessImportantByData = lessImportantService2.findByDate(userEntity, year, month, day);

        if (lessImportantByData.isPresent()) {
            return ResponseEntity.ok(lessImportantByData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("")
    public ResponseEntity<LessImportantEntity2> save(@PathVariable("username") String username,
            @Valid @RequestBody LessImportantEntity2 lessImportantEntity2){

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        lessImportantEntity2.setUserEntity(userEntity);

        LessImportantEntity2 newLessImportantRecord = lessImportantService2.save(lessImportantEntity2);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLessImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newLessImportantRecord);

    }

    @PutMapping("/{id}")
    public ResponseEntity<LessImportantEntity2> update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody LessImportantEntity2 lessImportantEntity2) throws URISyntaxException {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }
        LessImportantEntity2 newLessImportantRecord = lessImportantService2.update(userEntity, id, lessImportantEntity2);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLessImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newLessImportantRecord);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("username") String username,
                                       @PathVariable("id") Long id) {
        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        lessImportantService2.delete(userEntity, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }
}



























