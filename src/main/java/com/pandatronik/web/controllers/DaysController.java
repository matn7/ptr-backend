package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.service.DaysService;
import com.pandatronik.utils.AppConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/days")
public class DaysController extends Resource<DaysDTO, DaysEntity>  {

    public DaysController(DaysService daysService) {
        super(daysService);
    }
}
