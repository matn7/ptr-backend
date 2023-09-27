package com.pandatronik.web.controllers;

import com.pandatronik.backend.service.ImportantCrudService;
import com.pandatronik.backend.service.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
public abstract class Resource<DTO, Entity, ID> {

    protected final ResourceService<DTO, Entity, Long> taskService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DTO findById(@PathVariable("username") String username, @PathVariable("id") Long id) {
        return taskService.findById(username, id);
    }

    @GetMapping("/{year}/{month}/{day}")
    @ResponseStatus(HttpStatus.OK)
    public DTO findByDate(@PathVariable("username") String username,
            @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {

        return taskService.findByDate(username, year, month, day);
    }

    public abstract DTO save(@PathVariable("username") String username,
                                                @Valid @RequestBody DTO dto);

    public abstract DTO update(@PathVariable("username") String username,
        @Valid @RequestBody DTO dto);


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("username") String username, @PathVariable("id") Long id) {
        taskService.delete(username, id);
    }

}
