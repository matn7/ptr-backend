package com.pandatronik.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImportantIndexResponse {
    private ExtraordinaryResponse extraordinaryResponse;
    private ImportantResponse importantResponse;
    private ImportantResponse2 importantResponse2;
    private ImportantResponse3 importantResponse3;
    private DaysResponse daysResponse;
}
