package com.pandatronik.web.controllers;

import com.pandatronik.backend.service.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
public abstract class Resource<DTO, Entity> {

    protected final ResourceService<DTO, Entity> resourceService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DTO findById(@PathVariable("username") String username, @PathVariable("id") Long id) {
        return resourceService.findById(username, id);
    }

    @GetMapping("/{year}/{month}/{day}")
    @ResponseStatus(HttpStatus.OK)
    public DTO findByDate(@PathVariable("username") String username,
            @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {
        return resourceService.findByDate(username, year, month, day);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DTO save(@PathVariable("username") String username, @Valid @RequestBody DTO dto) {
        return resourceService.save(username, dto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public DTO update(@PathVariable("username") String username, @Valid @RequestBody DTO dto) {
        return resourceService.save(username, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DTO delete(@PathVariable("username") String username, @PathVariable("id") Long id) {
        DTO delete = resourceService.delete(username, id);
        return delete;
    }

}
