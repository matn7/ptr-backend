package com.pandatronik.web.controllers.statistics;

import com.pandatronik.backend.service.*;
import com.pandatronik.web.controllers.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pandatronik.utils.ApplicationUtils.API_VERSION;

@Component
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(API_VERSION + "{userProfileId}/statistics/lessimportant")
public class LessImportantStatisticsResource {

    private LessImportantService lessImportantService;
    private LessImportantService2 lessImportantService2;
    private LessImportantService3 lessImportantService3;

    @Autowired
    public LessImportantStatisticsResource(LessImportantService lessImportantService, LessImportantService2 lessImportantService2,
                                           LessImportantService3 lessImportantService3) {
        this.lessImportantService = lessImportantService;
        this.lessImportantService2 = lessImportantService2;
        this.lessImportantService3 = lessImportantService3;
    }

    @GetMapping("/1/count/{year}")
    public ResponseEntity<?> countTask1MadeInYear(@PathVariable("userProfileId") String userProfileId,
                                                  @PathVariable("year") int year) {
        List<Integer> result =  lessImportantService.countTaskMadeInYear(userProfileId, year);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/2/count/{year}")
    public ResponseEntity<?> countTask2MadeInYear(@PathVariable("userProfileId") String userProfileId,
                                              @PathVariable("year") int year) {
        List<Integer> result =  lessImportantService2.countTaskMadeInYear(userProfileId, year);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/3/count/{year}")
    public ResponseEntity<?> countTask3MadeInYear(@PathVariable("userProfileId") String userProfileId,
                                              @PathVariable("year") int year) {
        List<Integer> result =  lessImportantService3.countTaskMadeInYear(userProfileId, year);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/1/avg/{year}")
    public ResponseEntity<?> avgTask1MadeInMonthYear(@PathVariable("userProfileId") String userProfileId,
                                            @PathVariable("year") int year) {
        List<Double> result = lessImportantService.avgTaskMadeInMonthYear(userProfileId, year);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/2/avg/{year}")
    public ResponseEntity<?> avgTask2MadeInMonthYear(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("year") int year) {
        List<Double> result = lessImportantService2.avgTaskMadeInMonthYear(userProfileId, year);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/3/avg/{year}")
    public ResponseEntity<?> avgTask3MadeInMonthYear(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("year") int year) {
        List<Double> result = lessImportantService3.avgTaskMadeInMonthYear(userProfileId, year);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

}
