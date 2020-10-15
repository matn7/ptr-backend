package com.pandatronik.payload;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ExceptionResponse {
    private String body;
    private HttpStatus httpStatus;
}
