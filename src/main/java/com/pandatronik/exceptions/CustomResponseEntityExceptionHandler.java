package com.pandatronik.exceptions;

import com.pandatronik.payload.ExceptionResponse;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request) {
        return new ResponseEntity<>(ExceptionResponse.builder().body("Resource Not Found")
                .httpStatus(HttpStatus.NOT_FOUND).build(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "";
        if (ex.getBindingResult().getAllErrors().isEmpty()) {
            message = "Bad Request";
        } else {
            message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        return new ResponseEntity<>(ExceptionResponse.builder().body(message)
                .httpStatus(HttpStatus.BAD_REQUEST).build(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
