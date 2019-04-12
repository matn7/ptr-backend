package com.pandatronik.web.controllers;

import com.pandatronik.backend.service.StatisticsImportantIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/{userProfileId}/important")
public class ImportantIndexResource {

    private final StatisticsImportantIndexService statisticsImportantIndexService;

    @Autowired
    public ImportantIndexResource(StatisticsImportantIndexService statisticsImportantIndexService) {
        this.statisticsImportantIndexService = statisticsImportantIndexService;
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> findIndexData(@PathVariable("userProfileId") String userProfileId,
            @PathVariable("year") int year, @PathVariable("month") int month) {
        Optional<List<Object[]>> importantIndexData = statisticsImportantIndexService.findIndexData(userProfileId, year, month);
        if (importantIndexData.isPresent()) {
            return ResponseEntity.ok(importantIndexData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }
}
