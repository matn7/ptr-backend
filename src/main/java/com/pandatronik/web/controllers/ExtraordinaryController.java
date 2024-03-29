package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import com.pandatronik.backend.service.ExtraordinaryService;
import com.pandatronik.utils.AppConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/extraordinary")
public class ExtraordinaryController extends Resource<ExtraordinaryDTO, ExtraordinaryEntity> {

    public ExtraordinaryController(ExtraordinaryService extraordinaryService) {
        super(extraordinaryService);
    }
}
