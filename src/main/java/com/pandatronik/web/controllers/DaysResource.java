package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.DaysEntityDTO;
import com.pandatronik.backend.service.DaysService;
import com.pandatronik.backend.service.user.account.UserService;
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

// todo CrossOrigin and RequestMapping as Spring property
@Validated
@RestController
@RequestMapping(DaysResource.BASE_URL + "/users/{username}/days")
@RequiredArgsConstructor
public class DaysResource {

    public static final String BASE_URL = "/api/v1";

    private final DaysService daysService;
    private final UserService userService;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public DaysEntityDTO findDaysById(@PathVariable("username") String username, @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);
        checkUser(userEntity);

        return daysService.findById(userEntity, id);
    }

    @GetMapping("{year}/{month}/{day}")
    @ResponseStatus(HttpStatus.OK)
    public DaysEntityDTO findByDate(@PathVariable("username") String username, @PathVariable("year") int year,
            @PathVariable("month") int month, @PathVariable("day") int day) {

        UserEntity userEntity = userService.findByUserName(username);
        checkUser(userEntity);

        return daysService.findByDate(userEntity, day, month, year);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DaysEntityDTO save(@PathVariable("username") String username,
            @Valid @RequestBody DaysEntityDTO daysEntity) {

        UserEntity userEntity = userService.findByUserName(username);
        checkUser(userEntity);

        daysEntity.setUserEntity(userEntity);
        DaysEntityDTO newDaysRecord = daysService.save(daysEntity);

        return newDaysRecord;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DaysEntityDTO update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody DaysEntityDTO daysEntity) throws URISyntaxException {

        UserEntity userEntity = userService.findByUserName(username);
        checkUser(userEntity);
        daysEntity.setUserEntity(userEntity);

        return daysService.save(daysEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("username") String username, @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);
        checkUser(userEntity);

        daysService.delete(userEntity, id);
    }

    // this check method does not looks correctly spring should manage it somehow
    // when in request provide different username and password receive Invalid Username or password
    private void checkUser(UserEntity userEntity) {
//        if (isNull(userEntity)) {
//            throw new UserNotFoundException(messageSource.getMessage("user.not.found.message", null
//                    , LocaleContextHolder.getLocale()));
//        }
    }
}
