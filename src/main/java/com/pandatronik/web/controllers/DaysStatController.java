package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.repositories.model.DaysBetween;
import com.pandatronik.backend.persistence.repositories.model.DaysMonthDateAvgRate;
import com.pandatronik.backend.service.DaysService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.payload.YearBetweenRequest;
import com.pandatronik.payload.YearRequest;
import com.pandatronik.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/stat/days")
@RequiredArgsConstructor
public class DaysStatController {

    private final DaysService daysService;
    private final UserService userService;

    @PostMapping("/daysbetween")
    @ResponseStatus(HttpStatus.OK)
    public List<DaysBetween> findById(@PathVariable("username") String username,
                                      @Valid @RequestBody YearBetweenRequest yearBetweenRequest) {

        UserEntity userEntity = userService.findByUserName(username);

        List<DaysBetween> byYearRange = daysService.findByYearRange(userEntity, yearBetweenRequest.getYearStart(), yearBetweenRequest.getYearEnd());
        return byYearRange;
    }

    @PostMapping("/monthavgdaybyyear")
    @ResponseStatus(HttpStatus.OK)
    public List<DaysMonthDateAvgRate> findMonthAvgRateDayByYear(@PathVariable("username") String username,
                                      @Valid @RequestBody YearRequest yearRequest) {

        UserEntity userEntity = userService.findByUserName(username);

        List<DaysMonthDateAvgRate> monthAvgRate = daysService.findMonthAvgRateDayByYear(userEntity, yearRequest.getYear());
        return monthAvgRate;
    }
}
