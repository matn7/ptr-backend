package com.pandatronik.web.controllers.admin;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.web.controllers.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static java.util.Objects.nonNull;

@RestController
@RequestMapping("${api.version}")
@CrossOrigin(origins = "${angular.api.url}")
@AllArgsConstructor
public class AdminUserController {

    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping("/admin/all")
    public ResponseEntity<?> findAll() {
        Iterable<UserEntity> allUsers = userService.findAll();

        if (nonNull(allUsers)) {
            return ResponseEntity.status(HttpStatus.OK).body(allUsers);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(messageSource.getMessage("record.not.found.message", null
                            , LocaleContextHolder.getLocale())));
        }
    }
}
