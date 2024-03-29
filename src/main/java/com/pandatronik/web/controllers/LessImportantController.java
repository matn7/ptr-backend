package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.model.LessImportantDTO;
import com.pandatronik.backend.service.LessImportantService;
import com.pandatronik.utils.AppConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/lessimportant/1")
public class LessImportantController extends Resource<LessImportantDTO, LessImportantEntity> {

    public LessImportantController(LessImportantService taskService) {
        super(taskService);
    }

}



























