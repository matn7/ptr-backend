package com.pandatronik.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class YearRequest {

    @NotBlank
    @NotNull
    private String year;

}
