package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity2;
import com.pandatronik.backend.service.ImportantService;
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

import static com.pandatronik.utils.ApplicationUtils.ANGULAR_ORIGIN;
import static com.pandatronik.utils.ApplicationUtils.API_VERSION;
import static java.util.Objects.isNull;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/important/1")
public class ImportantResource {

    private final ImportantService importantService;

    private final UserService userService;

    @Autowired
    public ImportantResource(ImportantService importantService, UserService userService) {
        this.importantService = importantService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("username") String username,
            @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }

        Optional<ImportantEntity> importantById = importantService.findById(userEntity, id);

        if (importantById.isPresent()) {
            return ResponseEntity.ok(importantById.get());
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

        Optional<ImportantEntity> importantByData = importantService.findByDate(userEntity, year, month, day);

        if (importantByData.isPresent()) {
            return ResponseEntity.ok(importantByData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("")
    public ResponseEntity<ImportantEntity> save(@PathVariable("username") String username,
            @Valid @RequestBody ImportantEntity importantEntity){

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        importantEntity.setUserEntity(userEntity);

        ImportantEntity newImportantRecord = importantService.save(importantEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newImportantRecord);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ImportantEntity> update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody ImportantEntity importantEntity) throws URISyntaxException {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }
        ImportantEntity newImportantRecord = importantService.update(userEntity, id, importantEntity);


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

        if (isNull(userEntity)) {
            return null;
        }

        importantService.delete(userEntity, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }

}



























