package com.pandatronik.exceptions;

import com.pandatronik.payload.ExceptionResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler({ AuthenticationException.class })
//    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request) {
        return new ResponseEntity<>(ExceptionResponse.builder().body("Resource Not Fount").build(),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public ResponseEntity<Object> handleConstraintViolationException(Exception exception, WebRequest request) {
//        System.out.println();
//        return new ResponseEntity<>(ExceptionResponse.builder().body("Resource Not Fount").build(),
//                new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorMessages = new ArrayList<>();
        Set<String> affectedFields = new HashSet<>();
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        for(ObjectError error : errors) {
            String message = error.getDefaultMessage();
            errorMessages.add(message);
            String[] split = message.split(" ");
            affectedFields.add(split[0].toLowerCase());
        }
        ExceptionResponse responseModel = ExceptionResponse
                .builder()
                .errorMessages(errorMessages)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(status.value())
                .affectedFields(affectedFields)
                .build();
        return new ResponseEntity<>(responseModel,
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
