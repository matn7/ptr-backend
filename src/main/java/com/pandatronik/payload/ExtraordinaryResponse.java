package com.pandatronik.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExtraordinaryResponse {
    private Long id;
    private String title;
    private String body;
}
