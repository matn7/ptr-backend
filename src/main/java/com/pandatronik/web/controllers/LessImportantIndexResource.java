package com.pandatronik.web.controllers;

import com.pandatronik.backend.service.LessImportantIndexService;
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
@RequestMapping(API_VERSION + "{userProfileId}/lessimportant")
public class LessImportantIndexResource {

    private LessImportantIndexService lessImportantIndexService;

    @Autowired
    public LessImportantIndexResource(LessImportantIndexService lessImportantIndexService) {
        this.lessImportantIndexService = lessImportantIndexService;
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> findLessImportantByData(@PathVariable("userProfileId") String userProfileId,
                                                 @PathVariable("year") int year, @PathVariable("month") int month) {

        Optional<List<Object[]>> lessImportantIndexData = lessImportantIndexService.findLessImportantsIndexData(year, month, userProfileId);
        if (lessImportantIndexData.isPresent()) {
            return ResponseEntity.ok(lessImportantIndexData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }
}
