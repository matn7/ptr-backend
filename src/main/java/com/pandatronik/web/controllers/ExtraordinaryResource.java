package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.service.ExtraordinaryService;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.exceptions.UserNotFoundException;
import com.pandatronik.utils.HeaderUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import static java.util.Objects.isNull;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/extraordinary")
@AllArgsConstructor
public class ExtraordinaryResource {

    private final ExtraordinaryService extraordinaryService;
    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping("/all")
    public Iterable<ExtraordinaryEntity> findAll(@PathVariable("username") String username) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        Iterable<ExtraordinaryEntity> all = extraordinaryService.findAll(userEntity);
        return all;
    }

    @GetMapping("/{id}")
    public ExtraordinaryEntity findById(@PathVariable("username") String username, @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        return extraordinaryService.findById(userEntity, id);

    }

    @GetMapping("/{year}/{month}/{day}")
    public ExtraordinaryEntity findByDate(@PathVariable("username") String username, @PathVariable("year") int year,
            @PathVariable("month") int month, @PathVariable("day") int day) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

//        Optional<ExtraordinaryEntity> extraordinaryDaysByDayMonthYear =
//                extraordinaryService.findByDate(userEntity, day, month, year);
//
//        if (extraordinaryDaysByDayMonthYear.isPresent()) {
            return extraordinaryService.findByDate(userEntity, day, month, year);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ErrorMessage("Record not found"));
//        }
    }

    @PostMapping("")
    public ResponseEntity<ExtraordinaryEntity> save(@PathVariable("username") String username,
            @Valid @RequestBody ExtraordinaryEntity extraordinaryEntity) throws URISyntaxException {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        extraordinaryEntity.setUserEntity(userEntity);

        ExtraordinaryEntity newExtraordinaryRecord = extraordinaryService.save(extraordinaryEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newExtraordinaryRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newExtraordinaryRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExtraordinaryEntity> update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody ExtraordinaryEntity extraordinaryEntity) throws URISyntaxException {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        ExtraordinaryEntity newExtraordinaryRecord = extraordinaryService.update(id, extraordinaryEntity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newExtraordinaryRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newExtraordinaryRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("username") String username, @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        extraordinaryService.delete(userEntity, id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("A record with id: " + id + " has been deleted", String.valueOf(id))).build();
    }

    private void checkUser(UserEntity userEntity) {
        if (isNull(userEntity)) {
            throw new UserNotFoundException(messageSource.getMessage("user.not.found.message", null
                    , LocaleContextHolder.getLocale()));
        }
    }
}
