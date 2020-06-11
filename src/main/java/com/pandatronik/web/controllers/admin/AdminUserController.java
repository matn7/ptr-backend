package com.pandatronik.web.controllers.admin;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.payload.UserResponse;
import com.pandatronik.web.controllers.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("${api.version}")
@CrossOrigin(origins = {"${angular.api.url}", "${angular.api.admin.url}"})
@AllArgsConstructor
public class AdminUserController {

    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping("/admin/all")
    public ResponseEntity<?> findAll() {
        Iterable<UserEntity> allUsers = userService.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();

        prepareUserResponseList(allUsers, userResponseList);

        if (nonNull(userResponseList)) {
            return ResponseEntity.status(HttpStatus.OK).body(userResponseList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }

    private void prepareUserResponseList(Iterable<UserEntity> allUsers, List<UserResponse> userResponseList) {
        allUsers.forEach(user -> {
            UserResponse userResponse = UserResponse.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .enabled(user.isEnabled())
                    .plan(user.getPlan())
                    .build();
            userResponseList.add(userResponse);
        });
    }
}
