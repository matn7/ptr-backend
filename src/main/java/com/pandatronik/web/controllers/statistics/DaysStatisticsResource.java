package com.pandatronik.web.controllers.statistics;

import com.pandatronik.backend.service.DaysService;
import com.pandatronik.backend.service.StatisticsImportantIndexService;
import com.pandatronik.enums.RateDayEnum;
import com.pandatronik.payload.DateRequest;
import com.pandatronik.web.controllers.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.pandatronik.utils.ApplicationUtils.API_VERSION;

@Component
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/{userProfileId}/statistics/days")
public class DaysStatisticsResource {

    private final DaysService daysService;

    public DaysStatisticsResource(DaysService daysService) {
        this.daysService = daysService;
    }

    @PostMapping("/yearData")
    public ResponseEntity<?> yearlyMood(@PathVariable("userProfileId") String userProfileId,
                                         @RequestBody DateRequest dateRequest) {
        List<Integer> result = new ArrayList<>();
        List<Integer> res = daysService.findByYearAndRateDay2(userProfileId, dateRequest.getYear());

        Map<Integer, Long> collect = res.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if (result != null) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("/yearAvgData")
    public ResponseEntity<?> yearlyAvgMood(@PathVariable("userProfileId") String userProfileId,
                                        @RequestBody DateRequest dateRequest) {
        List<Double> result = daysService.findAverageByYear(userProfileId, dateRequest.getYear());
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("/monthData")
    public ResponseEntity<?> monthlyMood(@PathVariable("userProfileId") String userProfileId,
                                         @RequestBody DateRequest dateRequest) {
        List<Integer> result = new ArrayList<>();
        int day_100 = daysService.findByMonthYearAndRateDay(userProfileId, dateRequest.getMonth(),
                dateRequest.getYear(), RateDayEnum.VERY_GOOD.getRateDay());
        int day_75 = daysService.findByMonthYearAndRateDay(userProfileId, dateRequest.getMonth(),
                dateRequest.getYear(), RateDayEnum.GOOD.getRateDay());
        int day_50 = daysService.findByMonthYearAndRateDay(userProfileId, dateRequest.getMonth(),
                dateRequest.getYear(), RateDayEnum.OK.getRateDay());
        int day_25 = daysService.findByMonthYearAndRateDay(userProfileId, dateRequest.getMonth(),
                dateRequest.getYear(), RateDayEnum.BAD.getRateDay());
        int day_0 = daysService.findByMonthYearAndRateDay(userProfileId, dateRequest.getMonth(),
                dateRequest.getYear(), RateDayEnum.VERY_BAD.getRateDay());

        result.add(day_100);
        result.add(day_75);
        result.add(day_50);
        result.add(day_25);
        result.add(day_0);

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @PostMapping("/monthDaysData")
    public ResponseEntity<?> monthlyDaysMood(@PathVariable("userProfileId") String userProfileId,
                                         @RequestBody DateRequest dateRequest) {
        List<Integer> result = daysService.findRateDayData(userProfileId, dateRequest.getYear(), dateRequest.getMonth()).get();

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

}
