package com.pandatronik.backend.persistence.repositories.model;

import com.pandatronik.enums.RateDayEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DaysBetween {

    private RateDayEnum rateDay;
    private LocalDate startDate;

    public DaysBetween(RateDayEnum rateDay, LocalDate startDate) {
        this.rateDay = rateDay;
        this.startDate = startDate;
    }
}
