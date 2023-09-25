package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.model.LessImportant3DTO;
import com.pandatronik.backend.service.LessImportant3Service;
import com.pandatronik.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/lessimportant/3")
public class LessImportant3Controller extends Resource<LessImportant3DTO> {

    public LessImportant3Controller(LessImportant3Service taskService) {
        super(taskService);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessImportant3DTO save(@PathVariable("username") String username,
                                  @Valid @RequestBody LessImportant3DTO lessImportant3DTO){

        return taskService.save(username, lessImportant3DTO);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LessImportant3DTO update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody LessImportant3DTO lessImportant3DTO) {
        return taskService.update(id, lessImportant3DTO);
    }
}
