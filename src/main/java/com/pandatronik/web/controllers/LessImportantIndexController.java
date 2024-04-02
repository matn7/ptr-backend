package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.model.IndexDTO;
import com.pandatronik.backend.service.LessImportantIndexService;
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

    private final LessImportantIndexService lessImportantIndexService;

    @GetMapping("/{year}/{month}")
    public IndexDTO findByDate(@PathVariable("username") String username,
                               @PathVariable("year") int year, @PathVariable("month") int month) {
        return lessImportantIndexService.getData(username, year, month);
    }
}
