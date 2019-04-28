package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity3;
import com.pandatronik.backend.service.LessImportantService3;
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

import static java.util.Objects.isNull;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/lessimportant/3")
public class LessImportantResource3 {

    private final LessImportantService3 lessImportantService3;

    private final UserService userService;

    @Autowired
    public LessImportantResource3(LessImportantService3 lessImportantService3, UserService userService) {
        this.lessImportantService3 = lessImportantService3;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("username") String username,
                                      @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        Optional<LessImportantEntity3> lessImportantById = lessImportantService3.findById(userEntity, id);

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
            return null;
        }

        Optional<LessImportantEntity3> lessImportantByData = lessImportantService3.findByDate(userEntity, year, month, day);

        if (lessImportantByData.isPresent()) {
            return ResponseEntity.ok(lessImportantByData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("")
    public ResponseEntity<LessImportantEntity3> save(@PathVariable("username") String username,
                                                     @Valid @RequestBody LessImportantEntity3 lessImportantEntity3){

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        lessImportantEntity3.setUserEntity(userEntity);

        LessImportantEntity3 newLessImportantRecord = lessImportantService3.save(lessImportantEntity3);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLessImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newLessImportantRecord);

    }

    @PutMapping("/{id}")
    public ResponseEntity<LessImportantEntity3> update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody LessImportantEntity3 lessImportantEntity3) throws URISyntaxException {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        LessImportantEntity3 newLessImportantRecord = lessImportantService3.update(userEntity, id, lessImportantEntity3);


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

        System.out.println("DELETE");

        if (isNull(userEntity)) {
            return null;
        }

        lessImportantService3.delete(userEntity, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }

}



























