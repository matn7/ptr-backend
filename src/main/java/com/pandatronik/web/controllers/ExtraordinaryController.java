package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import com.pandatronik.backend.persistence.model.ExtraordinaryListDTO;
import com.pandatronik.backend.service.ExtraordinaryService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.utils.AppConstants;
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

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/extraordinary")
@AllArgsConstructor
public class ExtraordinaryController {

    private final ExtraordinaryService extraordinaryService;
    private final UserService userService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ExtraordinaryListDTO findAll(@PathVariable("username") String username) {

        UserEntity userEntity = userService.findByUserName(username);

        ExtraordinaryListDTO extraordinaryListDTO = new ExtraordinaryListDTO();
        extraordinaryListDTO.setExtraordinaryList(extraordinaryService.findAll(userEntity.getId()));

        return extraordinaryListDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExtraordinaryDTO findById(@PathVariable("username") String username, @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        return extraordinaryService.findById(userEntity.getId(), id);

    }

    @GetMapping("/{year}/{month}/{day}")
    @ResponseStatus(HttpStatus.OK)
    public ExtraordinaryDTO findByDate(@PathVariable("username") String username, @PathVariable("year") int year,
                                       @PathVariable("month") int month, @PathVariable("day") int day) {

        UserEntity userEntity = userService.findByUserName(username);

        return extraordinaryService.findByDate(userEntity.getId(), day, month, year);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExtraordinaryDTO save(@PathVariable("username") String username,
                                 @Valid @RequestBody ExtraordinaryDTO extraordinaryDTO) {

        UserEntity userEntity = userService.findByUserName(username);

        extraordinaryDTO.setUserEntityId(userEntity.getId());
        return extraordinaryService.save(extraordinaryDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExtraordinaryDTO update(@PathVariable("username") String username,
                                   @PathVariable("id") Long id, @Valid @RequestBody ExtraordinaryDTO extraordinaryDTO) {

        UserEntity userEntity = userService.findByUserName(username);
        extraordinaryDTO.setUserEntityId(userEntity.getId());

        return extraordinaryService.update(id, extraordinaryDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("username") String username, @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        extraordinaryService.delete(userEntity.getId(), id);
    }

}
