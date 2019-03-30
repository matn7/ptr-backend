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

import static com.pandatronik.utils.ApplicationUtils.API_VERSION;

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
    public ResponseEntity<?> countTask1MadeInYear(@PathVariable("userProfileId") String userProfileId,
                                                  @PathVariable("year") int year) {
        List<Integer> result =  importantService.countTaskMadeInYear(userProfileId, year);
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
        List<Integer> result =  importantService2.countTaskMadeInYear(userProfileId, year);
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
        List<Integer> result =  importantService3.countTaskMadeInYear(userProfileId, year);
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
        List<Double> result = importantService.avgTaskMadeInMonthYear(userProfileId, year);
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
        List<Double> result = importantService2.avgTaskMadeInMonthYear(userProfileId, year);
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
        List<Double> result = importantService3.avgTaskMadeInMonthYear(userProfileId, year);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

}
