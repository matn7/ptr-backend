package com.pandatronik.payload;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Set;

@Getter
@Builder
public class ExceptionResponse {
    private String body;
    private HttpStatus httpStatus;
    List<String> errorMessages;
    Set<String> affectedFields;
    int statusCode;
}
