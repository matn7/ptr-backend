package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.IndexDataService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.payload.ImportantIndexResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.isNull;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/important")
@AllArgsConstructor
public class ImportantIndexResource {

    private final UserService userService;
    private final IndexDataService<ImportantIndexResponse> prepareIndexDataService;
    private final MessageSource messageSource;

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> findIndexData(@PathVariable("username") String username,
            @PathVariable("year") int year, @PathVariable("month") int month) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }

        List<ImportantIndexResponse> importantData = prepareIndexDataService.getData(userEntity, year, month);

        if (!importantData.isEmpty()) {
            return ResponseEntity.ok(importantData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }
}
