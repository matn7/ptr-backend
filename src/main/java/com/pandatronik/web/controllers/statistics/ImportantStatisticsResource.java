package com.pandatronik.web.controllers.statistics;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.ImportantService;
import com.pandatronik.backend.service.Important2Service;
import com.pandatronik.backend.service.Important3Service;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.exceptions.UserNotFoundException;
import com.pandatronik.payload.StartEndRequest;
import com.pandatronik.web.controllers.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/statistics/important")
@AllArgsConstructor
public class ImportantStatisticsResource {

    private final ImportantService importantService;
    private final Important2Service important2Service;
    private final Important3Service important3Service;
    private final MessageSource messageSource;

    @GetMapping("/1/count/{year}")
    public ResponseEntity<?> findCountByYearTask1(@PathVariable("username") String username,
                                                  @PathVariable("year") int year) {
        List<Object[]> result =  importantService.findCountByYearStat(username, year);
        Map<Object, Object> collect = result.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
        if (nonNull(collect)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/2/count/{year}")
    public ResponseEntity<?> findCountByYearTask2(@PathVariable("username") String username,
                                              @PathVariable("year") int year) {

        List<Object[]> result =  important2Service.findCountByYearStat(username, year);
        Map<Object, Object> collect = result.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
        if (nonNull(collect)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/3/count/{year}")
    public ResponseEntity<?> findCountByYearTask3(@PathVariable("username") String username,
                                              @PathVariable("year") int year) {

        List<Object[]> result =  important3Service.findCountByYearStat(username, year);
        Map<Object, Object> collect = result.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
        if (nonNull(collect)) {
            // "SEVENTY_FIVE" -> "1"
            // "HUNDRED" -> "2"
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/1/avg/{year}")
    public ResponseEntity<?> findAverageByYearTask1(@PathVariable("username") String username,
                                            @PathVariable("year") int year) {

        List<Object[]> result = importantService.findAverageByYearStat(username, year);
        Map<Object, Object> collect = result.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
        if (nonNull(result)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/2/avg/{year}")
    public ResponseEntity<?> findAverageByYearTask2(@PathVariable("username") String username,
                                                @PathVariable("year") int year) {

        List<Object[]> result = important2Service.findAverageByYearStat(username, year);
        Map<Object, Object> collect = result.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
        if (nonNull(result)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/3/avg/{year}")
    public ResponseEntity<?> findAverageByYearTask3(@PathVariable("username") String username,
                                                @PathVariable("year") int year) {

        List<Object[]> result = important3Service.findAverageByYearStat(username, year);
        Map<Object, Object> collect = result.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
        if (nonNull(result)) {
            // "2" -> "0.0"
            // "5" -> "0.5"
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }

    @PostMapping("/1/startEnd")
    public ResponseEntity<?> findCountMadeByStartEnd(@PathVariable("username") String username,
            @RequestBody StartEndRequest startEndRequest) {

        List<Integer> countMadeByStartEnd = importantService.findCountMadeByStartEnd(username,
                startEndRequest.getStartDate(), startEndRequest.getEndDate());

        Map<Integer, Long> collect = countMadeByStartEnd.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // 0 - 100
        // 1 - 75
        // 2 - 50
        // 3 - 25
        // 4 - 0

        if (nonNull(collect)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }

    }

    @PostMapping("/2/startEnd")
    public ResponseEntity<?> findCountMadeByStartEnd2(@PathVariable("username") String username,
                                                     @RequestBody StartEndRequest startEndRequest) {

        List<Integer> countMadeByStartEnd = important2Service.findCountMadeByStartEnd(username,
                startEndRequest.getStartDate(), startEndRequest.getEndDate());

        Map<Integer, Long> collect = countMadeByStartEnd.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if (nonNull(collect)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }

    }

    @PostMapping("/3/startEnd")
    public ResponseEntity<?> findCountMadeByStartEnd3(@PathVariable("username") String username,
                                                     @RequestBody StartEndRequest startEndRequest) {

        List<Integer> countMadeByStartEnd = important3Service.findCountMadeByStartEnd(username,
                startEndRequest.getStartDate(), startEndRequest.getEndDate());

        Map<Integer, Long> collect = countMadeByStartEnd.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if (nonNull(collect)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }

    }

}
