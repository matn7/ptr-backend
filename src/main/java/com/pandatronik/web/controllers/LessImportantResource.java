package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity2;
import com.pandatronik.backend.service.LessImportantService;
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
@RequestMapping("${api.version}/users/{username}/lessimportant/1")
public class LessImportantResource {

    private final LessImportantService lessImportantService;

    private final UserService userService;


    @Autowired
    public LessImportantResource(LessImportantService lessImportantService, UserService userService) {
        this.lessImportantService = lessImportantService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("username") String username,
                                      @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        Optional<LessImportantEntity> lessImportantById = lessImportantService.findById(userEntity, id);

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

        Optional<LessImportantEntity> lessImportantByData = lessImportantService.findByDate(userEntity, year, month, day);

        if (lessImportantByData.isPresent()) {
            return ResponseEntity.ok(lessImportantByData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("")
    public ResponseEntity<LessImportantEntity> save(@PathVariable("username") String username,
                                                     @Valid @RequestBody LessImportantEntity lessImportantEntity){

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        lessImportantEntity.setUserEntity(userEntity);

        LessImportantEntity newLessImportantRecord = lessImportantService.save(lessImportantEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLessImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newLessImportantRecord);

    }

    @PutMapping("/{id}")
    public ResponseEntity<LessImportantEntity> update(@PathVariable("username") String username,
                                                       @PathVariable("id") Long id, @Valid @RequestBody LessImportantEntity lessImportantEntity) throws URISyntaxException {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        LessImportantEntity newLessImportantRecord = lessImportantService.update(userEntity, id, lessImportantEntity);


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

        lessImportantService.delete(userEntity, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable("userProfileId") String userProfileId,
//                                                @PathVariable("id") Long id) {
//        lessImportantService.delete(userProfileId, id);
//        return ResponseEntity.ok()
//                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
//    }
}



























