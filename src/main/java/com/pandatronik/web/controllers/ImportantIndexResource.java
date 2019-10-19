package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.StatisticsImportantIndexService;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import static java.util.Objects.isNull;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/important")
@AllArgsConstructor
public class ImportantIndexResource {

    private final UserService userService;
    private final StatisticsImportantIndexService statisticsImportantIndexService;
    private final MessageSource messageSource;

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> findIndexData(@PathVariable("username") String username,
            @PathVariable("year") int year, @PathVariable("month") int month) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("Record not found"));
        }

        Optional<List<Object[]>> importantIndexData = statisticsImportantIndexService.findIndexData(userEntity, year, month);
        if (importantIndexData.isPresent()) {
            return ResponseEntity.ok(importantIndexData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }
}
