package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.StatisticsLessImportantIndexService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.exceptions.UserNotFoundException;
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
@RequestMapping("${api.version}/users/{username}/lessimportant")
@AllArgsConstructor
public class LessImportantIndexResource {

    private final StatisticsLessImportantIndexService statisticsLessImportantIndexService;
    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> findLessImportantByData(@PathVariable("username") String username,
                                                 @PathVariable("year") int year, @PathVariable("month") int month) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            throw new UserNotFoundException(messageSource.getMessage("user.not.found.message", null
                    , LocaleContextHolder.getLocale()));
        }

        Optional<List<Object[]>> lessImportantIndexData =
                statisticsLessImportantIndexService.findIndexData(userEntity, year, month);
        if (lessImportantIndexData.isPresent()) {
            return ResponseEntity.ok(lessImportantIndexData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }
}
