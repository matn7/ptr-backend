package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.LessImportant3Entity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import com.pandatronik.backend.service.LessImportant3Service;
import com.pandatronik.utils.AppConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/lessimportant/3")
public class LessImportant3Controller extends Resource<TaskDTO, LessImportant3Entity> {

    public LessImportant3Controller(LessImportant3Service taskService) {
        super(taskService);
    }

}
