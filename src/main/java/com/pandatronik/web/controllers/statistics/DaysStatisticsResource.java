package com.pandatronik.web.controllers.statistics;

import com.pandatronik.backend.service.DaysService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/statistics/days")
@AllArgsConstructor
public class DaysStatisticsResource {

    private final DaysService daysService;
    private final MessageSource messageSource;
//
//    @PostMapping("/yearData")
//    public ResponseEntity<?> findByYearData(@PathVariable("username") String username,
//            @RequestBody DateRequest dateRequest) {
//        List<Integer> res = daysService.findByYearData(username, dateRequest.getYear());
//
//        Map<Integer, Long> collect = res.stream()
//                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//
//        if (nonNull(collect)) {
//            return ResponseEntity.ok(collect);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
//                            , LocaleContextHolder.getLocale())));
//        }
//    }
//
//    @PostMapping("/yearAvgData")
//    public ResponseEntity<?> findAverageByYearData(@PathVariable("username") String username,
//                                        @RequestBody DateRequest dateRequest) {
//
//        List<Object[]> res = daysService.findAverageByYearData(username, dateRequest.getYear());
//        Map<Object, Object> collect = res.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
//        if (nonNull(res)) {
//            return ResponseEntity.ok(collect);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
//                            , LocaleContextHolder.getLocale())));
//        }
//    }
//
//    @PostMapping("/monthData")
//    public ResponseEntity<?> findByMonthAndYearData(@PathVariable("username") String username,
//                                         @RequestBody DateRequest dateRequest) {
//
//        List<Object[]> res = daysService.findByMonthAndYearData(username, dateRequest.getMonth(),
//                dateRequest.getYear());
//        Map<Object, Object> collect = res.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
//
//        if (nonNull(collect)) {
//            return ResponseEntity.ok(collect);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
//                            , LocaleContextHolder.getLocale())));
//        }
//    }
//
//    @PostMapping("/monthDaysData")
//    public ResponseEntity<?> findByMonthAndYearDailyData(@PathVariable("username") String username,
//        @RequestBody DateRequest dateRequest) {
//
//        List<Integer> result = daysService.findByMonthAndYearDailyData(username, dateRequest.getYear(),
//                dateRequest.getMonth()).get();
//
//        if (result != null) {
//            return ResponseEntity.ok(result);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
//                            , LocaleContextHolder.getLocale())));
//        }
//    }


}
