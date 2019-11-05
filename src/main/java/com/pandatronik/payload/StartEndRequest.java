package com.pandatronik.payload;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
public class StartEndRequest {

    @NotBlank(message = "StartDate cannot be blank")
    private LocalDate startDate;

    @NotBlank(message = "EndDate cannot be blank")
    private LocalDate endDate;

}
