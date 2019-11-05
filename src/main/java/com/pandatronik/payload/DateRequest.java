package com.pandatronik.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DateRequest {

    @NotBlank(message = "Year cannot be blank")
    private Integer year;

    @NotBlank(message = "Month cannot be blank")
    private Integer month;

}
