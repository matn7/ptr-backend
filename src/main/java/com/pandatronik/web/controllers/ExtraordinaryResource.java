package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.service.ExtraordinaryService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.utils.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static com.pandatronik.utils.ApplicationUtils.API_VERSION;
import static java.util.Objects.isNull;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/extraordinary")
public class ExtraordinaryResource {

    private final ExtraordinaryService extraordinaryService;

    private final UserService userService;


    @Autowired
    public ExtraordinaryResource(ExtraordinaryService extraordinaryService, UserService userService) {
        this.extraordinaryService = extraordinaryService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public Iterable<ExtraordinaryEntity> findAll(@PathVariable("userProfileId") String userProfileId) {
        Iterable<ExtraordinaryEntity> all = extraordinaryService.findAll(userProfileId);
        return all;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("username") String username, @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User not found"));
        }

        Optional<ExtraordinaryEntity> extraordinaryDaysById = extraordinaryService.findById(userEntity, id);

        if (extraordinaryDaysById.isPresent()) {
            return ResponseEntity.ok(extraordinaryDaysById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<?> findByDate(@PathVariable("username") String username, @PathVariable("year") int year,
            @PathVariable("month") int month, @PathVariable("day") int day) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User not found"));
        }


        Optional<ExtraordinaryEntity> extraordinaryDaysByDayMonthYear =
                extraordinaryService.findByDate(userEntity, day, month, year);

        if (extraordinaryDaysByDayMonthYear.isPresent()) {
            return ResponseEntity.ok(extraordinaryDaysByDayMonthYear.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("")
    public ResponseEntity<ExtraordinaryEntity> save(@PathVariable("username") String username,
            @Valid @RequestBody ExtraordinaryEntity extraordinaryEntity) throws URISyntaxException {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        extraordinaryEntity.setUserEntity(userEntity);

        ExtraordinaryEntity newExtraordinaryRecord = extraordinaryService.save(extraordinaryEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newExtraordinaryRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newExtraordinaryRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExtraordinaryEntity> update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody ExtraordinaryEntity extraordinaryEntity) throws URISyntaxException {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        ExtraordinaryEntity newExtraordinaryRecord = extraordinaryService.update(userEntity, id, extraordinaryEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newExtraordinaryRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newExtraordinaryRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("username") String username, @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        extraordinaryService.delete(userEntity, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }
}
