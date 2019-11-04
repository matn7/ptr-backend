package com.pandatronik.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@RestController
//@ControllerAdvice
public class CustomeResponseEntityExceptionHandler {
//        extends ResponseEntityExceptionHandler {

//    @ExceptionHandler({ AuthenticationException.class })
//    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//    }

}
