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
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/{userProfileId}/extraordinary")
public class ExtraordinaryResource {

    private final ExtraordinaryService extraordinaryService;

    @Autowired
    public ExtraordinaryResource(ExtraordinaryService extraordinaryService) {
        this.extraordinaryService = extraordinaryService;
    }

    @GetMapping("/all")
    public Iterable<ExtraordinaryEntity> findAll(@PathVariable("userProfileId") String userProfileId) {
        Iterable<ExtraordinaryEntity> all = extraordinaryService.findAll(userProfileId);
        return all;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("userProfileId") String userProfileId, @PathVariable("id") Long id) {
        Optional<ExtraordinaryEntity> extraordinaryDaysById = extraordinaryService.findById(userProfileId, id);

        if (extraordinaryDaysById.isPresent()) {
            return ResponseEntity.ok(extraordinaryDaysById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<?> findByDate(@PathVariable("userProfileId") String userProfileId, @PathVariable("year") int year,
            @PathVariable("month") int month, @PathVariable("day") int day) {
        Optional<ExtraordinaryEntity> extraordinaryDaysByDayMonthYear =
                extraordinaryService.findByDate(userProfileId, day, month, year);

        if (extraordinaryDaysByDayMonthYear.isPresent()) {
            return ResponseEntity.ok(extraordinaryDaysByDayMonthYear.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("")
    public ResponseEntity<ExtraordinaryEntity> save(@PathVariable("userProfileId") String userProfileId,
            @Valid @RequestBody ExtraordinaryEntity extraordinaryEntity) throws URISyntaxException {
        ExtraordinaryEntity newExtraordinaryRecord = extraordinaryService.save(userProfileId, extraordinaryEntity);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/extraordinary/" + +newExtraordinaryRecord.getId()))
                .headers(HeaderUtil.createAlert( "A extraordinary record is created with identifier " + newExtraordinaryRecord.getId(),
                        String.valueOf(newExtraordinaryRecord.getId())))
                .body(newExtraordinaryRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExtraordinaryEntity> update(@PathVariable("userProfileId") String userProfileId,
            @PathVariable("id") Long id, @Valid @RequestBody ExtraordinaryEntity extraordinaryEntity) throws URISyntaxException {
        ExtraordinaryEntity newExtraordinaryRecord = extraordinaryService.update(userProfileId, id, extraordinaryEntity);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/extraordinary/" + +newExtraordinaryRecord.getId()))
                .headers(HeaderUtil.createAlert( "A extraordinary with identifier " + newExtraordinaryRecord.getId()
                                + " has been updated",
                        String.valueOf(newExtraordinaryRecord.getId())))
                .body(newExtraordinaryRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("userProfileId") String userProfileId, @PathVariable("id") Long id) {
        extraordinaryService.delete(userProfileId, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }
}
