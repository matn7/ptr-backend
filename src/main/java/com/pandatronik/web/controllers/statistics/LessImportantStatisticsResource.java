package com.pandatronik.web.controllers.statistics;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.*;
import com.pandatronik.backend.service.user.account.UserService;
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
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/statistics/lessimportant")
public class LessImportantStatisticsResource {

    private final UserService userService;

    private LessImportantService lessImportantService;
    private LessImportantService2 lessImportantService2;
    private LessImportantService3 lessImportantService3;

    @Autowired
    public LessImportantStatisticsResource(LessImportantService lessImportantService, LessImportantService2 lessImportantService2,
            LessImportantService3 lessImportantService3, UserService userService) {
        this.lessImportantService = lessImportantService;
        this.lessImportantService2 = lessImportantService2;
        this.lessImportantService3 = lessImportantService3;
        this.userService = userService;
    }

    @GetMapping("/1/count/{year}")
    public ResponseEntity<?> findCountByYearTask1(@PathVariable("username") String username,
                                                  @PathVariable("year") int year) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User not found"));
        }

        List<Object[]> result =  lessImportantService.findCountByYearStat(userEntity, year);
        Map<Object, Object> collect = result.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
        if (nonNull(collect)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/2/count/{year}")
    public ResponseEntity<?> findCountByYearTask2(@PathVariable("username") String username,
                                              @PathVariable("year") int year) {
        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User not found"));
        }

        List<Object[]> result =  lessImportantService2.findCountByYearStat(userEntity, year);
        Map<Object, Object> collect = result.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
        if (nonNull(collect)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/3/count/{year}")
    public ResponseEntity<?> findCountByYearTask3(@PathVariable("username") String username,
                                              @PathVariable("year") int year) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User not found"));
        }

        List<Object[]> result =  lessImportantService3.findCountByYearStat(userEntity, year);
        Map<Object, Object> collect = result.stream().collect(Collectors.toMap(elem -> elem[0], elem -> elem[1]));
        if (nonNull(collect)) {
            return ResponseEntity.ok(collect);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/1/avg/{year}")
    public ResponseEntity<?> findAverageByYearTask1(@PathVariable("username") String username,
                                            @PathVariable("year") int year) {
        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User not found"));
        }

        List<Object[]> result = lessImportantService.findAverageByYearStat(userEntity, year);
        if (nonNull(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/2/avg/{year}")
    public ResponseEntity<?> avgTask2MadeInMonthYear(@PathVariable("username") String username,
                                                @PathVariable("year") int year) {
        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User not found"));
        }

        List<Object[]> result = lessImportantService2.findAverageByYearStat(userEntity, year);
        if (nonNull(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

    @GetMapping("/3/avg/{year}")
    public ResponseEntity<?> avgTask3MadeInMonthYear(@PathVariable("username") String username,
                                                @PathVariable("year") int year) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("User not found"));
        }

        List<Object[]> result = lessImportantService3.findAverageByYearStat(userEntity, year);
        if (nonNull(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }
    }

}
