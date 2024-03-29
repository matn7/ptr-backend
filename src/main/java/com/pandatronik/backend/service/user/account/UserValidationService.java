package com.pandatronik.backend.service.user.account;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
import com.pandatronik.payload.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserValidationService {

    private static final Logger LOG = LoggerFactory.getLogger(UserValidationService.class);

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> validate(UserEntity user) {

        List<String> errorList = new ArrayList<>();
        Set<String> affectedFields = new HashSet<>();

        UserEntity localUser = userRepository.findByEmail(user.getEmail());
        Integer passwordLength = user.getPassword().length();

        if (localUser != null) {
            // todo: Display this info in UI
            LOG.info("User with username {} and email {} already exists. Nothing will be done. ",
                    user.getUsername(), user.getEmail());
            affectedFields.add("email");
            errorList.add("User with username '" + user.getUsername() + "' and email '" + user.getEmail() + "' already exists.");
        } else if (passwordLength < 6 || passwordLength > 30) {
            LOG.info("Password does not fulfill 6 - 30 length requirements. Password length {} ",
                    passwordLength);
            affectedFields.add("password");
            errorList.add("Password size must be between 6 and 30 characters");
        } else {
            return null;
        }

        ExceptionResponse responseModel = ExceptionResponse
                .builder()
                .errorMessages(errorList)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .affectedFields(affectedFields)
                .build();
        return new ResponseEntity<>(responseModel,
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
