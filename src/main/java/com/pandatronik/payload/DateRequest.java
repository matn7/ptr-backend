package com.pandatronik.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateRequest {

    @NotBlank(message = "Year cannot be blank")
    private Integer year;

    @NotBlank(message = "Month cannot be blank")
    private Integer month;

}
