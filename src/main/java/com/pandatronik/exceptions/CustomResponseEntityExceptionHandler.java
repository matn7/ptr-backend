package com.pandatronik.exceptions;

import com.pandatronik.payload.ExceptionResponse;
import com.pandatronik.web.controllers.users.UserController;
import jakarta.mail.AuthenticationFailedException;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
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

    private static final Logger LOG = LoggerFactory.getLogger(CustomResponseEntityExceptionHandler.class);

//    @ExceptionHandler({ AuthenticationException.class })
//    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//    }
    
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request) {
        List<String> errorMessages = new ArrayList<>();
        Set<String> affectedFields = new HashSet<>();
        errorMessages.add("Resource Not Found");
        ExceptionResponse responseModel = ExceptionResponse
                .builder()
                .body("Resource Not Found")
                .errorMessages(errorMessages)
                .httpStatus(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .affectedFields(affectedFields)
                .build();
        return new ResponseEntity<>(responseModel,
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public ResponseEntity<Object> handleConstraintViolationException(Exception exception, WebRequest request) {
//        System.out.println();
//        return new ResponseEntity<>(ExceptionResponse.builder().body("Resource Not Fount").build(),
//                new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }

    // {
    //      "errorStatus":400,
    //      "errorMsg":{
    //          "body":null,
    //          "httpStatus":"BAD_REQUEST",
    //          "errorMessages":["must not be blank","Password must contain at least one number, one special character, one upper and lower case letter"],
    //          "affectedFields":["password","must"],
    //          "statusCode":400
    //      }
    // }

    // {"errorStatus":400,"errorMsg":["User with username 'maja_1992' and email 'mateusz123@panda.com' already exists."]}

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

    // {
    //      "errorStatus":401,
    //      "errorMsg":{
    //          "body":null,
    //          "httpStatus":"UNAUTHORIZED",
    //          "errorMessages":["User is disabled"],
    //          "affectedFields":["password","username"],
    //          "statusCode":401
    //      }
    // }

    @ExceptionHandler({AuthenticationException.class})
    protected ResponseEntity<Object> handleAuthenticationException(Exception exception, WebRequest request) {
        List<String> errorMessages = new ArrayList<>();
        Set<String> affectedFields = new HashSet<>();
        errorMessages.add(exception.getMessage());
        affectedFields.add("username");
        affectedFields.add("password");
        ExceptionResponse responseModel = ExceptionResponse
                .builder()
                .errorMessages(errorMessages)
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .affectedFields(affectedFields)
                .build();
        return new ResponseEntity<>(responseModel,
                new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handleDataIntegrityException(Exception exception, WebRequest request) {
        LOG.info("Duplicate username");
        // Duplicate entry 'matek_1991'
        List<String> errorMessages = new ArrayList<>();
        Set<String> affectedFields = new HashSet<>();
        errorMessages.add("Username already exists");
        affectedFields.add("username");
        ExceptionResponse responseModel = ExceptionResponse
                .builder()
                .errorMessages(errorMessages)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .affectedFields(affectedFields)
                .build();
        return new ResponseEntity<>(responseModel,
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthenticationFailedException.class})
    protected ResponseEntity<Object> handleRegistrationEmailException(Exception exception, WebRequest request) {
        LOG.info("Failure during registration process");
        // Duplicate entry 'matek_1991'
        List<String> errorMessages = new ArrayList<>();
        Set<String> affectedFields = new HashSet<>();
        errorMessages.add("Internal pandatronik error during registration process!");
        affectedFields.add("");
        ExceptionResponse responseModel = ExceptionResponse
                .builder()
                .errorMessages(errorMessages)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .affectedFields(affectedFields)
                .build();
        return new ResponseEntity<>(responseModel,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
