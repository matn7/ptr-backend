package com.pandatronik.web.controllers;

import com.pandatronik.backend.service.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
public abstract class Resource<DTO, Entity> {

    protected final ResourceService<DTO, Entity> resourceService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DTO> findById(@PathVariable("username") String username, @PathVariable("id") Long id) {
        DTO dto = resourceService.findById(username, id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/{year}/{month}/{day}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DTO> findByDate(@PathVariable("username") String username,
            @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {
        DTO dto = resourceService.findByDate(username, year, month, day);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DTO> save(@PathVariable("username") String username, @Valid @RequestBody DTO dto) {
        DTO createDto = resourceService.save(username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DTO> update(@PathVariable("username") String username, @Valid @RequestBody DTO dto) {
        DTO updateDto = resourceService.save(username, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DTO> delete(@PathVariable("username") String username, @PathVariable("id") Long id) {
        DTO delete = resourceService.delete(username, id);
        return ResponseEntity.status(HttpStatus.OK).body(delete);
    }

}
