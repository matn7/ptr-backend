package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.StatisticsLessImportantIndexService;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.pandatronik.utils.ApplicationUtils.API_VERSION;
import static java.util.Objects.isNull;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/lessimportant")
public class LessImportantIndexResource {

    private final StatisticsLessImportantIndexService statisticsLessImportantIndexService;
    private final UserService userService;

    @Autowired
    public LessImportantIndexResource(StatisticsLessImportantIndexService statisticsLessImportantIndexService,
                                      UserService userService) {
        this.statisticsLessImportantIndexService = statisticsLessImportantIndexService;
        this.userService = userService;
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> findLessImportantByData(@PathVariable("username") String username,
                                                 @PathVariable("year") int year, @PathVariable("month") int month) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }


        Optional<List<Object[]>> lessImportantIndexData =
                statisticsLessImportantIndexService.findIndexData(userEntity, year, month);
        if (lessImportantIndexData.isPresent()) {
            return ResponseEntity.ok(lessImportantIndexData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }
}
