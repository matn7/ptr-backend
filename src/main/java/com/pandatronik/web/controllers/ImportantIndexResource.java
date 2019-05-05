package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.StatisticsImportantIndexService;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/important")
public class ImportantIndexResource {

    private final UserService userService;
    private final StatisticsImportantIndexService statisticsImportantIndexService;

    @Autowired
    public ImportantIndexResource(StatisticsImportantIndexService statisticsImportantIndexService,
                                  UserService userService) {
        this.statisticsImportantIndexService = statisticsImportantIndexService;
        this.userService = userService;
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> findIndexData(@PathVariable("username") String username,
            @PathVariable("year") int year, @PathVariable("month") int month) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }

        Optional<List<Object[]>> importantIndexData = statisticsImportantIndexService.findIndexData(userEntity, year, month);
        if (importantIndexData.isPresent()) {
            return ResponseEntity.ok(importantIndexData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }
}
