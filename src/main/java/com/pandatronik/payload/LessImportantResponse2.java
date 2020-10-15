package com.pandatronik.payload;

import com.pandatronik.enums.MadeEnum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class LessImportantResponse2 {
    private Long id;
    private String title;
    private String body;
    private MadeEnum made;
    private LocalDateTime postedOn;
    private LocalDate startDate;
}
