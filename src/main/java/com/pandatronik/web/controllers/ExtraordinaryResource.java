package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.service.ExtraordinaryService;
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
@RequestMapping(API_VERSION + "{userProfileId}/extraordinary")
public class ExtraordinaryResource {

    private ExtraordinaryService extraordinaryService;

    @Autowired
    public ExtraordinaryResource(ExtraordinaryService extraordinaryService) {
        this.extraordinaryService = extraordinaryService;
    }

    @GetMapping("/all")
    public Iterable<ExtraordinaryEntity> fetchAll(@PathVariable("userProfileId") String userProfileId) {
        Iterable<ExtraordinaryEntity> all = extraordinaryService.getAll(userProfileId);
        return all;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchExtraordinaryDaysById(@PathVariable("userProfileId") String userProfileId, @PathVariable("id") Long id) {
        Optional<ExtraordinaryEntity> extraordinaryDaysById = extraordinaryService.getExtraordinaryByUidId(userProfileId, id);

        if (extraordinaryDaysById.isPresent()) {
            return ResponseEntity.ok(extraordinaryDaysById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<?> fetchExtraordinaryDays(@PathVariable("userProfileId") String userProfileId, @PathVariable("year") int year,
                                                      @PathVariable("month") int month, @PathVariable("day") int day) {
        Optional<ExtraordinaryEntity> extraordinaryDaysByDayMonthYear = extraordinaryService.getExtraordinaryByUidDayMonthYear(userProfileId, day, month, year);

        if (extraordinaryDaysByDayMonthYear.isPresent()) {
            return ResponseEntity.ok(extraordinaryDaysByDayMonthYear.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("")
    public ResponseEntity<ExtraordinaryEntity> insertNewExtraordinaryDay(@PathVariable("userProfileId") String userProfileId,
                                                                         @Valid @RequestBody ExtraordinaryEntity extraordinaryEntity) throws URISyntaxException {
        ExtraordinaryEntity newExtraordinaryRecord = extraordinaryService.insertExtraordinary(userProfileId, extraordinaryEntity);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/extraordinary/" + +newExtraordinaryRecord.getId()))
                .headers(HeaderUtil.createAlert( "A extraordinary record is created with identifier " + newExtraordinaryRecord.getId(),
                        String.valueOf(newExtraordinaryRecord.getId())))
                .body(newExtraordinaryRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExtraordinaryEntity> updateExtraordinaryDay(@PathVariable("userProfileId") String userProfileId,
                                                       @PathVariable("id") Long id, @Valid @RequestBody ExtraordinaryEntity extraordinaryEntity) throws URISyntaxException {
        ExtraordinaryEntity newExtraordinaryRecord = extraordinaryService.updateExtraordinary(extraordinaryEntity);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/extraordinary/" + +newExtraordinaryRecord.getId()))
                .headers(HeaderUtil.createAlert( "A extraordinary with identifier " + newExtraordinaryRecord.getId()
                                + " has been updated",
                        String.valueOf(newExtraordinaryRecord.getId())))
                .body(newExtraordinaryRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteExtraordinaryDay(@PathVariable("userProfileId") String userProfileId, @PathVariable("id") Long id) {
        extraordinaryService.removeExtraordinary(userProfileId, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }
}
