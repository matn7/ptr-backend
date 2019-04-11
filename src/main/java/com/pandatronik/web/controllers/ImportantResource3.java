package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity3;
import com.pandatronik.backend.service.ImportantService3;
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

import static com.pandatronik.utils.ApplicationUtils.API_VERSION;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/{userProfileId}/important/3")
public class ImportantResource3 {

    private ImportantService3 importantService;

    @Autowired
    public ImportantResource3(ImportantService3 importantService) {
        this.importantService = importantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("id") Long id) {
        Optional<ImportantEntity3> importantById = importantService.findById(userProfileId, id);

        if (importantById.isPresent()) {
            return ResponseEntity.ok(importantById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<?> findByDate(@PathVariable("userProfileId") String userProfileId,
            @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {
        Optional<ImportantEntity3> importantByData = importantService.findByDate(userProfileId, year, month, day);

        if (importantByData.isPresent()) {
            return ResponseEntity.ok(importantByData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }

    }

    @PostMapping("")
    public ResponseEntity<ImportantEntity3> save(@PathVariable("userProfileId") String userProfileId,
            @Valid @RequestBody ImportantEntity3 importantEntity) throws URISyntaxException {
        ImportantEntity3 newImportantRecord = importantService.save(userProfileId, importantEntity);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/important/3/" + +newImportantRecord.getId()))
                .headers(HeaderUtil.createAlert( "A user is created with identifier " + newImportantRecord.getId(),
                        String.valueOf(newImportantRecord.getId())))
                .body(newImportantRecord);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ImportantEntity3> update(@PathVariable("userProfileId") String userProfileId,
            @PathVariable("id") Long id, @Valid @RequestBody ImportantEntity3 importantEntity) throws URISyntaxException {
        ImportantEntity3 newImportantRecord = importantService.update(userProfileId, id, importantEntity);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/important/3/" + +newImportantRecord.getId()))
                .headers(HeaderUtil.createAlert( "A important with identifier " + newImportantRecord.getId()
                                + " has been updated",
                        String.valueOf(newImportantRecord.getId())))
                .body(newImportantRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("id") Long id) {
        importantService.delete(userProfileId, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }
}



























