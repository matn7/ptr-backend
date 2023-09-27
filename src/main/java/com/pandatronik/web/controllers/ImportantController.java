package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.model.ImportantDTO;
import com.pandatronik.backend.service.ImportantService;
import com.pandatronik.backend.service.ResourceService;
import com.pandatronik.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/important/1")
public class ImportantController extends Resource<ImportantDTO> {

    public ImportantController(ImportantService importantService) {
        super(importantService);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImportantDTO save(@PathVariable("username") String username,
                             @Valid @RequestBody ImportantDTO importantDTO) {
        return taskService.save(username, importantDTO);
    }

    @Override
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ImportantDTO update(@PathVariable("username") String username,
                               @Valid @RequestBody ImportantDTO importantDTO) {
        return taskService.save(username, importantDTO);
    }
}
