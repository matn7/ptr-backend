package com.pandatronik.web.controllers.statistics;

import com.pandatronik.backend.service.DaysService;
import com.pandatronik.enums.RateDayEnum;
import com.pandatronik.payload.DateRequest;
import com.pandatronik.web.controllers.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Component
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/{userProfileId}/statistics/days")
public class DaysStatisticsResource {

    private final DaysService daysService;

    @Autowired
    public DaysStatisticsResource(DaysService daysService) {
        this.daysService = daysService;
    }

    @PostMapping("/yearData")
    public ResponseEntity<?> findByYearData(@PathVariable("userProfileId") String userProfileId,
            @RequestBody DateRequest dateRequest) {
        List<Integer> res = daysService.findByYearData(userProfileId, dateRequest.getYear());

        Map<Integer, Long> collect = res.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if (nonNull(collect)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("/yearAvgData")
    public ResponseEntity<?> findAverageByYearData(@PathVariable("userProfileId") String userProfileId,
                                        @RequestBody DateRequest dateRequest) {
        List<Object[]> res = daysService.findAverageByYearData(userProfileId, dateRequest.getYear());

        if (nonNull(res)) {
            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("/monthData")
    public ResponseEntity<?> findByMonthAndYearData(@PathVariable("userProfileId") String userProfileId,
                                         @RequestBody DateRequest dateRequest) {


        List<Object[]> res = daysService.findByMonthAndYearData(userProfileId, dateRequest.getMonth(),
                dateRequest.getYear());
        Map<Object, Object> collect = res.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));

        if (nonNull(collect)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("/monthDaysData")
    public ResponseEntity<?> findByMonthAndYearDailyData(@PathVariable("userProfileId") String userProfileId,
                                         @RequestBody DateRequest dateRequest) {
        List<Integer> result = daysService.findByMonthAndYearDailyData(userProfileId, dateRequest.getYear(),
                dateRequest.getMonth()).get();

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

}
