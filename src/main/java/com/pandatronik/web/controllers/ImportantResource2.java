package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity2;
import com.pandatronik.backend.service.ImportantService2;
import com.pandatronik.utils.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static com.pandatronik.utils.ApplicationUtils.API_VERSION;

@Validated
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(API_VERSION + "{userProfileId}/important/2")
public class ImportantResource2 {

    private ImportantService2 importantService;

    @Autowired
    public ImportantResource2(ImportantService2 importantService) {
        this.importantService = importantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchImportantById(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("id") Long id) {
        Optional<ImportantEntity2> importantById = importantService.getImportantByUidId(userProfileId, id);

        if (importantById.isPresent()) {
            return ResponseEntity.ok(importantById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<?> findImportantByData(@PathVariable("userProfileId") String userProfileId,
                                                 @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {
        Optional<ImportantEntity2> importantByData = importantService.getImportantByUidYearMonthDay(userProfileId, year, month, day);

        if (importantByData.isPresent()) {
            return ResponseEntity.ok(importantByData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }

    }

    @PostMapping("")
    public ResponseEntity<ImportantEntity2> insertNewImportantRecord(@PathVariable("userProfileId") String userProfileId,
                                                                     @Valid @RequestBody ImportantEntity2 importantEntity) throws URISyntaxException {

        ImportantEntity2 newImportantRecord = importantService.insertImportantRecord(userProfileId, importantEntity);
        return ResponseEntity.created(new URI("api/v1/" + userProfileId+ "/important/2/" + +newImportantRecord.getId()))
                .headers(HeaderUtil.createAlert( "A user is created with identifier " + newImportantRecord.getId(),
                        String.valueOf(newImportantRecord.getId())))
                .body(newImportantRecord);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ImportantEntity2> updateImportant(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("id") Long id, @Valid @RequestBody ImportantEntity2 importantEntity) throws URISyntaxException {

        ImportantEntity2 newImportantRecord = importantService.updateImportantRecord(userProfileId, id, importantEntity);
        return ResponseEntity.created(new URI("api/v1/" + userProfileId+ "/important/2/" + +newImportantRecord.getId()))
                .headers(HeaderUtil.createAlert( "A important with identifier " + newImportantRecord.getId()
                                + " has been updated",
                        String.valueOf(newImportantRecord.getId())))
                .body(newImportantRecord);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImportant(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("id") Long id) {
        importantService.deleteImportantRecord(userProfileId, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }
}



























