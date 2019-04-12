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
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/{userProfileId}/days")
public class DaysResource {

    private DaysService daysService;

    @Autowired
    public DaysResource(DaysService daysService) {
        this.daysService = daysService;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("userProfileId") String userProfileId, @PathVariable("id") Long id) {
        Optional<DaysEntity> daysById = daysService.findById(userProfileId, id);
        if (daysById.isPresent()) {
            return ResponseEntity.ok(daysById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("{year}/{month}/{day}")
    public ResponseEntity<?> findByDate(@PathVariable("userProfileId") String userProfileId, @PathVariable("year") int year,
            @PathVariable("month") int month, @PathVariable("day") int day) {
        Optional<DaysEntity> daysByDayMonthYear = daysService.findByDate(userProfileId, day, month, year);

        if (daysByDayMonthYear.isPresent()) {
            return ResponseEntity.ok(daysByDayMonthYear.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("")
    public ResponseEntity<DaysEntity> save(@PathVariable("userProfileId") String userProfileId,
            @Valid @RequestBody DaysEntity daysEntity) throws URISyntaxException {
        DaysEntity newDaysRecord = daysService.save(userProfileId, daysEntity);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/days/" + newDaysRecord.getId()))
                .headers(HeaderUtil.createAlert( "A Days is created with identifier " + newDaysRecord.getId(),
                        String.valueOf(newDaysRecord.getId())))
                .body(newDaysRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DaysEntity> update(@PathVariable("userProfileId") String userProfileId,
            @PathVariable("id") Long id, @Valid @RequestBody DaysEntity daysEntity) throws URISyntaxException {
        DaysEntity updatedDaysRecord = daysService.update(userProfileId, id, daysEntity);
        return ResponseEntity.created(new URI(API_VERSION + userProfileId+ "/days/" + updatedDaysRecord.getId()))
                .headers(HeaderUtil.createAlert( "A Days has been updated with identifier " + updatedDaysRecord.getId(),
                        String.valueOf(updatedDaysRecord.getId())))
                .body(updatedDaysRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("userProfileId") String userProfileId, @PathVariable("id") Long id) {
        daysService.delete(userProfileId, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted",
                        String.valueOf(id))).build();
    }
}
