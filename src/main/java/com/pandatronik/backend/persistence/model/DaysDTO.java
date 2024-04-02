package com.pandatronik.backend.persistence.model;

import com.pandatronik.enums.MadeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DaysDTO extends CommonDTO {

    @NotNull
    private MadeEnum rateDay;

}