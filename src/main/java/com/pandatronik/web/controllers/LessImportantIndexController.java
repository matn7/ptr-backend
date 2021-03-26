package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.LessImportantIndexDTO;
import com.pandatronik.backend.service.LessImportantIndexService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/lessimportant")
@AllArgsConstructor
public class LessImportantIndexController {

    private final UserService userService;
    private final LessImportantIndexService lessImportantIndexService;

    @GetMapping("/{year}/{month}")
    public LessImportantIndexDTO findByDate(@PathVariable("username") String username,
                                            @PathVariable("year") int year, @PathVariable("month") int month) {
        final UserEntity userEntity = userService.findByUserName(username);
        return lessImportantIndexService.getData(userEntity, year, month);
    }
}
