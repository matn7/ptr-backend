package com.pandatronik.payload;

import javax.validation.constraints.NotBlank;

public class DateRequest {
    @NotBlank(message = "Year cannot be blank")
    private Integer year;
    @NotBlank(message = "Month cannot be blank")
    private Integer month;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}
