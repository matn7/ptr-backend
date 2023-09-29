package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import com.pandatronik.backend.persistence.model.ExtraordinaryListDTO;
import com.pandatronik.backend.service.ExtraordinaryService;
import com.pandatronik.backend.service.ResourceService;
import com.pandatronik.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/extraordinary")
public class ExtraordinaryController extends Resource<ExtraordinaryDTO, ExtraordinaryEntity, Long> {

    public ExtraordinaryController(ExtraordinaryService extraordinaryService) {
        super(extraordinaryService);
    }
}
