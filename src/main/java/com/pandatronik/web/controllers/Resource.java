package com.pandatronik.web.controllers;

import com.pandatronik.backend.service.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DTO save(@PathVariable("username") String username,
                             @Valid @RequestBody DTO dto) {
        return taskService.save(username, dto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public DTO update(@PathVariable("username") String username,
                               @Valid @RequestBody DTO dto) {
        return taskService.save(username, dto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("username") String username, @PathVariable("id") Long id) {
        taskService.delete(username, id);
    }

}
