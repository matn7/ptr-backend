package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.service.DaysService;
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
@RequestMapping("${api.version}/users/{username}/days")
public class DaysResource {

    private final DaysService daysService;

    private final UserService userService;


    @Autowired
    public DaysResource(DaysService daysService, UserService userService) {
        this.daysService = daysService;
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("username") String username, @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User not found"));
        }

        Optional<DaysEntity> daysById = daysService.findById(userEntity, id);
        if (daysById.isPresent()) {
            return ResponseEntity.ok(daysById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("{year}/{month}/{day}")
    public ResponseEntity<?> findByDate(@PathVariable("username") String username, @PathVariable("year") int year,
            @PathVariable("month") int month, @PathVariable("day") int day) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User not found"));
        }

        Optional<DaysEntity> daysByDayMonthYear = daysService.findByDate(userEntity, day, month, year);

        if (daysByDayMonthYear.isPresent()) {
            return ResponseEntity.ok(daysByDayMonthYear.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("")
    public ResponseEntity<DaysEntity> save(@PathVariable("username") String username,
            @Valid @RequestBody DaysEntity daysEntity) throws URISyntaxException {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        daysEntity.setUserEntity(userEntity);

        DaysEntity newDaysRecord = daysService.save(daysEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDaysRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newDaysRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DaysEntity> update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody DaysEntity daysEntity) throws URISyntaxException {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        DaysEntity updatedDaysRecord = daysService.update(userEntity, id, daysEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedDaysRecord.getId()).toUri();

        return ResponseEntity.created(location).body(updatedDaysRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("username") String username, @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return null;
        }

        daysService.delete(userEntity, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted",
                        String.valueOf(id))).build();
    }
}
