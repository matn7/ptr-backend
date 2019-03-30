package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.service.DaysService;
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
@RequestMapping(API_VERSION + "{userProfileId}/days")
public class DaysResource {

    private DaysService daysService;

    @Autowired
    public DaysResource(DaysService daysService) {
        this.daysService = daysService;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> fetchDaysById(@PathVariable("userProfileId") String userProfileId, @PathVariable("id") Long id) {
        Optional<DaysEntity> daysById = daysService.getDaysByUidId(userProfileId, id);
        if (daysById.isPresent()) {
            return ResponseEntity.ok(daysById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("{year}/{month}/{day}")
    public ResponseEntity<?> fetchDays(@PathVariable("userProfileId") String userProfileId, @PathVariable("year") int year,
                                @PathVariable("month") int month, @PathVariable("day") int day) {
        Optional<DaysEntity> daysByDayMonthYear = daysService.getDaysByUidDayMonthYear(userProfileId, day, month, year);

        if (daysByDayMonthYear.isPresent()) {
            return ResponseEntity.ok(daysByDayMonthYear.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("")
    public ResponseEntity<DaysEntity> insertNewDay(@PathVariable("userProfileId") String userProfileId,
                                                   @Valid @RequestBody DaysEntity daysEntity) throws URISyntaxException {
        DaysEntity newDaysRecord = daysService.insertDay(userProfileId, daysEntity);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/days/" + newDaysRecord.getId()))
                .headers(HeaderUtil.createAlert( "A Days is created with identifier " + newDaysRecord.getId(),
                        String.valueOf(newDaysRecord.getId())))
                .body(newDaysRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DaysEntity> updateDay(@PathVariable("userProfileId") String userProfileId,
                                          @PathVariable("id") Long id,
                                          @Valid @RequestBody DaysEntity daysEntity) throws URISyntaxException {
        DaysEntity updatedDaysRecord = daysService.updateDay(userProfileId, id, daysEntity);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/days/" + updatedDaysRecord.getId()))
                .headers(HeaderUtil.createAlert( "A Days has been updated with identifier " + updatedDaysRecord.getId(),
                        String.valueOf(updatedDaysRecord.getId())))
                .body(updatedDaysRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDay(@PathVariable("userProfileId") String userProfileId, @PathVariable("id") Long id) {
        daysService.removeDay(userProfileId, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }
}
