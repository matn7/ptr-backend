package com.pandatronik.backend.persistence.repositories.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaysMonthDateAvgRate {

    private int startDate;
    private double rateDay;

    public DaysMonthDateAvgRate(int startDate, double rateDay) {
        this.startDate = startDate;
        this.rateDay = rateDay;
    }
}
