package com.pandatronik.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class YearBetweenRequest {

    @NotNull(message = "Year start cannot be blank")
    private Integer yearStart;

    @NotNull(message = "Year end cannot be blank")
    private Integer yearEnd;

}
