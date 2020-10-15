package com.pandatronik.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LessImportantIndexResponse {
    private ExtraordinaryResponse extraordinaryResponse;
    private LessImportantResponse lessImportantResponse;
    private LessImportantResponse2 lessImportantResponse2;
    private LessImportantResponse3 lessImportantResponse3;
    private DaysResponse daysResponse;
}
