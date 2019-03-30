package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.service.ImportantService;
import com.pandatronik.utils.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static com.pandatronik.utils.ApplicationUtils.ANGULAR_ORIGIN;
import static com.pandatronik.utils.ApplicationUtils.API_VERSION;

@Validated
@CrossOrigin(origins = ANGULAR_ORIGIN)
@RestController
@RequestMapping(API_VERSION + "{userProfileId}/important/1")
public class ImportantResource {

    private ImportantService importantService;

    @Autowired
    public ImportantResource(ImportantService importantService) {
        this.importantService = importantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchImportantById(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("id") Long id) {
        Optional<ImportantEntity> importantById = importantService.getImportantByUidId(userProfileId, id);

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
        Optional<ImportantEntity> importantByData = importantService.getImportantByUidYearMonthDay(userProfileId, year, month, day);

        if (importantByData.isPresent()) {
            return ResponseEntity.ok(importantByData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }

    }

    @PostMapping("")
    public ResponseEntity<ImportantEntity> insertNewImportantRecord(@PathVariable("userProfileId") String userProfileId,
                                                                    @Valid @RequestBody ImportantEntity importantEntity) throws URISyntaxException {
        ImportantEntity newImportantRecord = importantService.insertImportantRecord(userProfileId, importantEntity);


        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/important/1/" + +newImportantRecord.getId()))
                .headers(HeaderUtil.createAlert( "A important is created with identifier " + newImportantRecord.getId(),
                        String.valueOf(newImportantRecord.getId())))
                .body(newImportantRecord);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ImportantEntity> updateImportant(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("id") Long id, @Valid @RequestBody ImportantEntity importantEntity) throws URISyntaxException {

        ImportantEntity newImportantRecord = importantService.updateImportantRecord(userProfileId, id, importantEntity);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/important/1/" + +newImportantRecord.getId()))
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



























