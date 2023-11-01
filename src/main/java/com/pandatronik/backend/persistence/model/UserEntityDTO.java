package com.pandatronik.backend.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pandatronik.backend.persistence.domain.PasswordResetToken;
import com.pandatronik.backend.persistence.domain.Plan;
import com.pandatronik.backend.persistence.domain.UserRole;
import com.pandatronik.backend.persistence.domain.core.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
//@Builder
@NoArgsConstructor
public class UserEntityDTO {

    private long id;

    @NotNull
    @NotBlank(message = "Username must not be blank")
    @Size(min = 6, max = 20, message = "Username size must be between 6 and 20")
    @Column(unique = true)
    private String username;

    @NotNull
    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, max = 60, message = "Password size must be between 6 and 20")
    @Column(name = "password")
    private String password;
    // Password is stored in DB table in such format (60 chars), that why max should be different in DTO:
    // $2a$10$3MK16ys612eX0KzFqs7aUOc6Ffji/UoXhJ0JsYC.srNhbbzAHydWC

    @Transient
    private String confirmPassword;

    @NotNull
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email address invalid")
    @Column(unique = true)
    private String email;

    @NotNull
    @NotBlank(message = "FirstName must not be blank")
    @Size(max = 20, message = "FirstName size must be between 1 and 20")
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @NotBlank(message = "LastName must not be blank")
    @Size(max = 20, message = "LastName size must be between 1 and 20")
    @Column(name = "last_name")
    private String lastName;

    private boolean enabled;

    /* Many users with the same Plan*/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, // if we delete user we want to their token be deleted too
            fetch = FetchType.EAGER,
            mappedBy = "user")
    private Set<PasswordResetToken> passwordResetTokens = new HashSet<>();

    // Tesks, Days
    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<ImportantEntity> importantEntity;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<Important2Entity> important2Entity;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<Important3Entity> important3Entity;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<LessImportantEntity> lessImportantEntity;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<LessImportant2Entity> lessImportant2Entity;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<LessImportant3Entity> lessImportant3Entity;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<DaysEntity> daysEntity;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)
    private List<ExtraordinaryEntity> extraordinaryEntity;

}
