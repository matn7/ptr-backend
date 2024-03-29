package com.pandatronik.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class StartEndRequest {

    @NotBlank(message = "StartDate cannot be blank")
    private LocalDate startDate;

    @NotBlank(message = "EndDate cannot be blank")
    private LocalDate endDate;

}
