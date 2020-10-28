package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.service.DaysService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.utils.AppConstants;
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

import javax.validation.Valid;
import java.net.URISyntaxException;

@Validated
@RestController
@RequestMapping(AppConstants.BASE_URL + "/{username}/days")
@RequiredArgsConstructor
public class DaysController {

    private final DaysService daysService;
    private final UserService userService;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public DaysDTO findById(@PathVariable("username") String username, @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        return daysService.findById(userEntity, id);
    }

    @GetMapping("{year}/{month}/{day}")
    @ResponseStatus(HttpStatus.OK)
    public DaysDTO findByDate(@PathVariable("username") String username, @PathVariable("year") int year,
                              @PathVariable("month") int month, @PathVariable("day") int day) {

        UserEntity userEntity = userService.findByUserName(username);

        return daysService.findByDate(userEntity, day, month, year);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DaysDTO save(@PathVariable("username") String username,
                        @Valid @RequestBody DaysDTO daysDTO) {

        UserEntity userEntity = userService.findByUserName(username);
        daysDTO.setUserEntity(userEntity);

        return daysService.save(daysDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DaysDTO update(@PathVariable("username") String username,
                          @PathVariable("id") Long id, @Valid @RequestBody DaysDTO daysDTO) throws URISyntaxException {

        UserEntity userEntity = userService.findByUserName(username);
        daysDTO.setUserEntity(userEntity);

        return daysService.save(daysDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("username") String username, @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);
        daysService.delete(userEntity, id);
    }

}
