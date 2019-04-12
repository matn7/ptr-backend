package com.pandatronik.web.controllers;

import com.pandatronik.backend.service.StatisticsLessImportantIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.pandatronik.utils.ApplicationUtils.API_VERSION;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/{userProfileId}/lessimportant")
public class LessImportantIndexResource {

    private StatisticsLessImportantIndexService statisticsLessImportantIndexService;

    @Autowired
    public LessImportantIndexResource(StatisticsLessImportantIndexService statisticsLessImportantIndexService) {
        this.statisticsLessImportantIndexService = statisticsLessImportantIndexService;
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> findLessImportantByData(@PathVariable("userProfileId") String userProfileId,
                                                 @PathVariable("year") int year, @PathVariable("month") int month) {

        Optional<List<Object[]>> lessImportantIndexData =
                statisticsLessImportantIndexService.findIndexData(userProfileId, year, month);
        if (lessImportantIndexData.isPresent()) {
            return ResponseEntity.ok(lessImportantIndexData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }
}
