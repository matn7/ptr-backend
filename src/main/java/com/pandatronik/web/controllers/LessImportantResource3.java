package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.LessImportantEntity3;
import com.pandatronik.backend.service.LessImportantService3;
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
@RequestMapping("${api.version}/{userProfileId}/lessimportant/3")
public class LessImportantResource3 {

    private LessImportantService3 lessImportantService3;

    @Autowired
    public LessImportantResource3(LessImportantService3 lessImportantService3) {
        this.lessImportantService3 = lessImportantService3;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("userProfileId") String userProfileId,
            @PathVariable("id") Long id) {
        Optional<LessImportantEntity3> lessImportantById = lessImportantService3.findById(userProfileId, id);

        if (lessImportantById.isPresent()) {
            return ResponseEntity.ok(lessImportantById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<?> findByDate(@PathVariable("userProfileId") String userProfileId,
            @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {
        Optional<LessImportantEntity3> lessImportantByData = lessImportantService3.findByDate(userProfileId, year, month, day);

        if (lessImportantByData.isPresent()) {
            return ResponseEntity.ok(lessImportantByData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("")
    public ResponseEntity<LessImportantEntity3> save(@PathVariable("userProfileId") String userProfileId,
                                                                    @Valid @RequestBody LessImportantEntity3 lessImportantEntity3) throws URISyntaxException {
        LessImportantEntity3 newLessImportantRecord = lessImportantService3.save(userProfileId, lessImportantEntity3);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/lessimportant/3/" + newLessImportantRecord.getId()))
                .headers(HeaderUtil.createAlert( "A record is created with identifier " + newLessImportantRecord.getId(),
                        String.valueOf(newLessImportantRecord.getId())))
                .body(newLessImportantRecord);

    }

    @PutMapping("/{id}")
    public ResponseEntity<LessImportantEntity3> update(@PathVariable("userProfileId") String userProfileId,
            @PathVariable("id") Long id, @Valid @RequestBody LessImportantEntity3 lessImportantEntity3) throws URISyntaxException {
        LessImportantEntity3 newLessImportantRecord = lessImportantService3.update(userProfileId, id, lessImportantEntity3);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/lessimportant/3/" + newLessImportantRecord.getId()))
                .headers(HeaderUtil.createAlert( "A less important with identifier " + newLessImportantRecord.getId()
                                + " has been updated",
                        String.valueOf(newLessImportantRecord.getId())))
                .body(newLessImportantRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("userProfileId") String userProfileId,
            @PathVariable("id") Long id) {
        lessImportantService3.delete(userProfileId, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }
}



























