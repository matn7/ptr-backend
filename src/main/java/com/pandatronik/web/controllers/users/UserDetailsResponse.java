package com.pandatronik.web.controllers.users;

import com.pandatronik.backend.persistence.domain.Plan;
import com.pandatronik.backend.persistence.domain.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public final class UserDetailsResponse {
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String username;

    private final boolean enabled;
    private final Plan plan;
    private final Set<UserRole> userRoles;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Plan getPlan() {
        return plan;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }
}
