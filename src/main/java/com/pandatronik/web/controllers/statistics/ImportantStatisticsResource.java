package com.pandatronik.web.controllers.statistics;

import com.pandatronik.backend.service.ImportantService;
import com.pandatronik.backend.service.ImportantService2;
import com.pandatronik.backend.service.ImportantService3;
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
@RequestMapping(API_VERSION + "{userProfileId}/statistics/important")
public class ImportantStatisticsResource {

    private final ImportantService importantService;
    private final ImportantService2 importantService2;
    private final ImportantService3 importantService3;

    public ImportantStatisticsResource(ImportantService importantService, ImportantService2 importantService2,
                                       ImportantService3 importantService3) {
        this.importantService = importantService;
        this.importantService2 = importantService2;
        this.importantService3 = importantService3;
    }

    @GetMapping("/1/count/{year}")
    public ResponseEntity<?> findCountByYearTask1(@PathVariable("userProfileId") String userProfileId,
                                                  @PathVariable("year") int year) {
        List<Object[]> result =  importantService.findCountByYearStat(userProfileId, year);
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
        List<Object[]> result =  importantService2.findCountByYearStat(userProfileId, year);
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
        List<Object[]> result =  importantService3.findCountByYearStat(userProfileId, year);
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
        List<Object[]> result = importantService.findAverageByYearStat(userProfileId, year);
        if (nonNull(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/2/avg/{year}")
    public ResponseEntity<?> findAverageByYearTask2(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("year") int year) {
        List<Object[]> result = importantService2.findAverageByYearStat(userProfileId, year);
        if (nonNull(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/3/avg/{year}")
    public ResponseEntity<?> findAverageByYearTask3(@PathVariable("userProfileId") String userProfileId,
                                                @PathVariable("year") int year) {
        List<Object[]> result = importantService3.findAverageByYearStat(userProfileId, year);
        if (nonNull(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

}
