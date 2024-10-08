package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.core.LessImportant2Entity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import com.pandatronik.backend.service.LessImportant2Service;
import com.pandatronik.utils.AppConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/lessimportant/2")
public class LessImportant2Controller extends Resource<TaskDTO, LessImportant2Entity> {

    public LessImportant2Controller(LessImportant2Service taskService) {
        super(taskService);
    }

}



























