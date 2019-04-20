package com.pandatronik.web.controllers.users;

import com.pandatronik.backend.persistence.domain.Plan;
import com.pandatronik.backend.persistence.domain.UserRole;

import java.util.Set;

public final class UserDetailsResponse {
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String username;

    private final boolean enabled;
    private final Plan plan;
    private final Set<UserRole> userRoles;

    public UserDetailsResponse(String email, String firstName, String lastName, String username,
                               boolean enabled, Plan plan, Set<UserRole> userRoles) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.enabled = enabled;
        this.plan = plan;
        this.userRoles = userRoles;
    }

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
