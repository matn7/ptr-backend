package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.LessImportantEntity2;
import com.pandatronik.backend.service.LessImportantService2;
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
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(API_VERSION + "{userProfileId}/lessimportant/2")
public class LessImportantResource2 {

    private LessImportantService2 lessImportantService2;

    @Autowired
    public LessImportantResource2(LessImportantService2 lessImportantService2) {
        this.lessImportantService2 = lessImportantService2;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchLessImportantById(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("id") Long id) {
        Optional<LessImportantEntity2> lessImportantById = lessImportantService2.getLessImportantByUidId(userProfileId, id);

        if (lessImportantById.isPresent()) {
            return ResponseEntity.ok(lessImportantById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<?> findLessImportantByData(@PathVariable("userProfileId") String userProfileId,
                                                 @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {
        Optional<LessImportantEntity2> lessImportantByData = lessImportantService2.getLessImportantByUidYearMonthDay(userProfileId, year, month, day);

        if (lessImportantByData.isPresent()) {
            return ResponseEntity.ok(lessImportantByData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("")
    public ResponseEntity<LessImportantEntity2> insertNewLessImportantRecord(@PathVariable("userProfileId") String userProfileId,
                                                                    @Valid @RequestBody LessImportantEntity2 lessImportantEntity2) throws URISyntaxException {
        LessImportantEntity2 newLessImportantRecord = lessImportantService2.insertLessImportantRecord(userProfileId, lessImportantEntity2);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/lessimportant/2/" + newLessImportantRecord.getId()))
                .headers(HeaderUtil.createAlert( "A user is created with identifier " + newLessImportantRecord.getId(),
                        String.valueOf(newLessImportantRecord.getId())))
                .body(newLessImportantRecord);

    }

    @PutMapping("/{id}")
    public ResponseEntity<LessImportantEntity2> updateLessImportant(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("id") Long id, @Valid @RequestBody LessImportantEntity2 lessImportantEntity2) throws URISyntaxException {

        LessImportantEntity2 newLessImportantRecord = lessImportantService2.updateLessImportantRecord(userProfileId, id, lessImportantEntity2);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/lessimportant/2/" + newLessImportantRecord.getId()))
                .headers(HeaderUtil.createAlert( "A less important with identifier " + newLessImportantRecord.getId()
                                + " has been updated",
                        String.valueOf(newLessImportantRecord.getId())))
                .body(newLessImportantRecord);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLessImportant(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("id") Long id) {
        lessImportantService2.deleteLessImportantRecord(userProfileId, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }
}



























