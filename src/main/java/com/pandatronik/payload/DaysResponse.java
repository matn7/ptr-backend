package com.pandatronik.payload;

import com.pandatronik.enums.MadeEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DaysResponse {
    private Long id;
    private MadeEnum rateDay;
}
