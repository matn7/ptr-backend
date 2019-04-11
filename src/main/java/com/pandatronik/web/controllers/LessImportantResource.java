package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.service.LessImportantService;
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
@RequestMapping("${api.version}/{userProfileId}/lessimportant/1")
public class LessImportantResource {

    private LessImportantService lessImportantService;

    @Autowired
    public LessImportantResource(LessImportantService lessImportantService) {
        this.lessImportantService = lessImportantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("userProfileId") String userProfileId,
            @PathVariable("id") Long id) {
        Optional<LessImportantEntity> lessImportantById = lessImportantService.findById(userProfileId, id);

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
        Optional<LessImportantEntity> lessImportantByData = lessImportantService.findByDate(userProfileId, year, month, day);

        if (lessImportantByData.isPresent()) {
            return ResponseEntity.ok(lessImportantByData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }

    }

    @PostMapping("")
    public ResponseEntity<LessImportantEntity> save(@PathVariable("userProfileId") String userProfileId,
            @Valid @RequestBody LessImportantEntity lessImportantEntity) throws URISyntaxException {
        LessImportantEntity newLessImportantRecord = lessImportantService.save(userProfileId, lessImportantEntity);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/lessimportant/1/" + newLessImportantRecord.getId()))
                .headers(HeaderUtil.createAlert( "A less important is created with identifier " + newLessImportantRecord.getId(),
                        String.valueOf(newLessImportantRecord.getId())))
                .body(newLessImportantRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessImportantEntity> update(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("id") Long id, @Valid @RequestBody LessImportantEntity lessImportantEntity) throws URISyntaxException {

        LessImportantEntity newLessImportantRecord = lessImportantService.update(userProfileId, id, lessImportantEntity);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/lessimportant/1/" + newLessImportantRecord.getId()))
                .headers(HeaderUtil.createAlert( "A less important with identifier " + newLessImportantRecord.getId()
                                + " has been updated",
                        String.valueOf(newLessImportantRecord.getId())))
                .body(newLessImportantRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("id") Long id) {
        lessImportantService.delete(userProfileId, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }
}



























