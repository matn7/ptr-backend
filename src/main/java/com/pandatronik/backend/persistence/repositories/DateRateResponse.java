package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.enums.RateDayEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DateRateResponse {

    private LocalDate startDate;
    private RateDayEnum rateDay;

}
