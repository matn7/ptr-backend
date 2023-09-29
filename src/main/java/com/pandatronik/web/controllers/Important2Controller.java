package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.service.Important2Service;
import com.pandatronik.utils.AppConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/important/2")
public class Important2Controller extends Resource<Important2DTO, Important2Entity, Long> {

    public Important2Controller(Important2Service importantService) {
        super(importantService);
    }

}
