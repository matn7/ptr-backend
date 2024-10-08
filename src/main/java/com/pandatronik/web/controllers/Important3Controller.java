package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import com.pandatronik.backend.service.Important3Service;
import com.pandatronik.utils.AppConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/important/3")
public class Important3Controller extends Resource<TaskDTO, Important3Entity> {

    public Important3Controller(Important3Service importantService) {
        super(importantService);
    }

}
