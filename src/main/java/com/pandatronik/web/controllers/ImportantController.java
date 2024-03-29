package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import com.pandatronik.backend.service.ImportantService;
import com.pandatronik.utils.AppConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/important/1")
public class ImportantController extends Resource<ImportantDTO, ImportantEntity> {

    public ImportantController(ImportantService importantService) {
        super(importantService);
    }

}
