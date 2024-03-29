package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.model.Important3DTO;
import com.pandatronik.backend.service.DaysService;
import com.pandatronik.backend.service.ResourceService;
import com.pandatronik.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import java.net.URISyntaxException;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/days")
public class DaysController extends Resource<DaysDTO, DaysEntity>  {

    public DaysController(DaysService daysService) {
        super(daysService);
    }
}
