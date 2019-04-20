package com.pandatronik.web.controllers.admin;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.user.account.UserService;
import com.pandatronik.web.controllers.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("${api.version}")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminUserController {

    @Autowired
    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/all")
    public ResponseEntity<?> findAll() {
        Iterable<UserEntity> allUsers = userService.findAll();

        if (nonNull(allUsers)) {
            return ResponseEntity.status(HttpStatus.OK).body(allUsers);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Not found"));
        }
    }
}
