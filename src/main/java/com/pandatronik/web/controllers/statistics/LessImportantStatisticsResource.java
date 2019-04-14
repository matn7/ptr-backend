package com.pandatronik.web.controllers.statistics;

import com.pandatronik.backend.service.*;
import com.pandatronik.web.controllers.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pandatronik.utils.ApplicationUtils.API_VERSION;
import static java.util.Objects.nonNull;

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
    public ResponseEntity<?> findCountByYearTask1(@PathVariable("userProfileId") String userProfileId,
                                                  @PathVariable("year") int year) {
        List<Object[]> result =  lessImportantService.findCountByYearStat(userProfileId, year);
        Map<Object, Object> collect = result.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
        if (nonNull(collect)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/2/count/{year}")
    public ResponseEntity<?> findCountByYearTask2(@PathVariable("userProfileId") String userProfileId,
                                              @PathVariable("year") int year) {
        List<Object[]> result =  lessImportantService2.findCountByYearStat(userProfileId, year);
        Map<Object, Object> collect = result.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
        if (nonNull(collect)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/3/count/{year}")
    public ResponseEntity<?> findCountByYearTask3(@PathVariable("userProfileId") String userProfileId,
                                              @PathVariable("year") int year) {
        List<Object[]> result =  lessImportantService3.findCountByYearStat(userProfileId, year);
        Map<Object, Object> collect = result.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
        if (nonNull(collect)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/1/avg/{year}")
    public ResponseEntity<?> findAverageByYearTask1(@PathVariable("userProfileId") String userProfileId,
                                            @PathVariable("year") int year) {
        List<Object[]> result = lessImportantService.findAverageByYearStat(userProfileId, year);
        if (nonNull(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/2/avg/{year}")
    public ResponseEntity<?> avgTask2MadeInMonthYear(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("year") int year) {
        List<Object[]> result = lessImportantService2.findAverageByYearStat(userProfileId, year);
        if (nonNull(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/3/avg/{year}")
    public ResponseEntity<?> avgTask3MadeInMonthYear(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("year") int year) {
        List<Object[]> result = lessImportantService3.findAverageByYearStat(userProfileId, year);
        if (nonNull(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

}
