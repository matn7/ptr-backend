package com.pandatronik.web.controllers;

import com.pandatronik.backend.service.ImportantIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static com.pandatronik.utils.ApplicationUtils.API_VERSION;

@Validated
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(API_VERSION + "{userProfileId}/important")
public class ImportantIndexResource {

    private ImportantIndexService importantIndexService;

    @Autowired
    public ImportantIndexResource(ImportantIndexService importantIndexService) {
        this.importantIndexService = importantIndexService;
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> findImportantByData(@PathVariable("userProfileId") String userProfileId,
                                                 @PathVariable("year") int year, @PathVariable("month") int month) {

        Optional<List<Object[]>> importantIndexData = importantIndexService.findImportantsIndexData(year, month, userProfileId);
        if (importantIndexData.isPresent()) {
            return ResponseEntity.ok(importantIndexData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }
}
