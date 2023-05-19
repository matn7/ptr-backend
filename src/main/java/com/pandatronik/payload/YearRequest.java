package com.pandatronik.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class YearRequest {

    @NotNull(message = "Year end cannot be blank")
    private Integer year;

}
