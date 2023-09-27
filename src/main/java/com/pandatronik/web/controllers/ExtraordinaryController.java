package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import com.pandatronik.backend.persistence.model.ExtraordinaryListDTO;
import com.pandatronik.backend.service.ExtraordinaryService;
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
@AllArgsConstructor
public class ExtraordinaryController {

    private final ExtraordinaryService extraordinaryService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ExtraordinaryListDTO findAll(@PathVariable("username") String username) {
        ExtraordinaryListDTO extraordinaryListDTO = new ExtraordinaryListDTO();
        extraordinaryListDTO.getExtraordinaryList().addAll(extraordinaryService.findAll(username));
        return extraordinaryListDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExtraordinaryDTO findById(@PathVariable("username") String username, @PathVariable("id") Long id) {
        return extraordinaryService.findById(username, id);

    }

    @GetMapping("/{year}/{month}/{day}")
    @ResponseStatus(HttpStatus.OK)
    public ExtraordinaryDTO findByDate(@PathVariable("username") String username, @PathVariable("year") int year,
                                       @PathVariable("month") int month, @PathVariable("day") int day) {
        return extraordinaryService.findByDate(username, day, month, year);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExtraordinaryDTO save(@PathVariable("username") String username,
                                 @Valid @RequestBody ExtraordinaryDTO extraordinaryDTO) {
        return extraordinaryService.save(username, extraordinaryDTO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ExtraordinaryDTO update(@PathVariable("username") String username,
                                   @Valid @RequestBody ExtraordinaryDTO extraordinaryDTO) {
        return extraordinaryService.save(username, extraordinaryDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("username") String username, @PathVariable("id") Long id) {
        extraordinaryService.delete(username, id);
    }

}
