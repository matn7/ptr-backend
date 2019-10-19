package com.pandatronik.web.controllers;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity3;
import com.pandatronik.backend.service.LessImportantService3;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.exceptions.UserNotFoundException;
import com.pandatronik.utils.HeaderUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import static java.util.Objects.isNull;

@Validated
@CrossOrigin(origins = "${angular.api.url}")
@RestController
@RequestMapping("${api.version}/users/{username}/lessimportant/3")
@AllArgsConstructor
public class LessImportantResource3 {

    private final LessImportantService3 lessImportantService3;
    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("username") String username,
                                      @PathVariable("id") Long id) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        Optional<LessImportantEntity3> lessImportantById = lessImportantService3.findById(userEntity, id);

        if (lessImportantById.isPresent()) {
            return ResponseEntity.ok(lessImportantById.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }

    @GetMapping("/{year}/{month}/{day}")
    public ResponseEntity<?> findByDate(@PathVariable("username") String username,
            @PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        Optional<LessImportantEntity3> lessImportantByData = lessImportantService3.findByDate(userEntity, year, month, day);

        if (lessImportantByData.isPresent()) {
            return ResponseEntity.ok(lessImportantByData.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }

    @PostMapping("")
    public ResponseEntity<LessImportantEntity3> save(@PathVariable("username") String username,
                                                     @Valid @RequestBody LessImportantEntity3 lessImportantEntity3){

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        lessImportantEntity3.setUserEntity(userEntity);

        LessImportantEntity3 newLessImportantRecord = lessImportantService3.save(lessImportantEntity3);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLessImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newLessImportantRecord);

    }

    @PutMapping("/{id}")
    public ResponseEntity<LessImportantEntity3> update(@PathVariable("username") String username,
            @PathVariable("id") Long id, @Valid @RequestBody LessImportantEntity3 lessImportantEntity3) throws URISyntaxException {

        UserEntity userEntity = userService.findByUserName(username);

        checkUser(userEntity);

        LessImportantEntity3 newLessImportantRecord = lessImportantService3.update(userEntity, id, lessImportantEntity3);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newLessImportantRecord.getId()).toUri();

        return ResponseEntity.created(location).body(newLessImportantRecord);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("username") String username,
            @PathVariable("id") Long id) {
        UserEntity userEntity = userService.findByUserName(username);

        System.out.println("DELETE");

        checkUser(userEntity);

        lessImportantService3.delete(userEntity, id);
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



























