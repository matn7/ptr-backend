package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.LessImportantIndexDTO;
import com.pandatronik.backend.service.IndexDataService;
import com.pandatronik.backend.service.LessImportantIndexService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.exceptions.UserNotFoundException;
import com.pandatronik.payload.LessImportantIndexResponse;
import com.pandatronik.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.isNull;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/lessimportant")
@AllArgsConstructor
public class LessImportantIndexController {

    private final UserService userService;
    private final IndexDataService<LessImportantIndexResponse> prepareLessImportantIndexDataService;
    private final MessageSource messageSource;
    private final LessImportantIndexService lessImportantIndexService;

    @GetMapping("/new/{year}/{month}")
    public LessImportantIndexDTO findByDate(@PathVariable("username") String username,
                                            @PathVariable("year") int year, @PathVariable("month") int month) {
        final UserEntity userEntity = userService.findByUserName(username);
        return lessImportantIndexService.getData(userEntity, year, month);
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> findLessImportantByData(@PathVariable("username") String username,
                                                     @PathVariable("year") int year, @PathVariable("month") int month) {

        UserEntity userEntity = userService.findByUserName(username);

        if (isNull(userEntity)) {
            throw new UserNotFoundException(messageSource.getMessage("user.not.found.message", null
                    , LocaleContextHolder.getLocale()));
        }

        List<LessImportantIndexResponse> lessImportantData = prepareLessImportantIndexDataService.getData(userEntity, year, month);

        if (!lessImportantData.isEmpty()) {
            return ResponseEntity.ok(lessImportantData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }
}
