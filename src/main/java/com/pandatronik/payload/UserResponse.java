package com.pandatronik.payload;

import com.pandatronik.backend.persistence.domain.Plan;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private Plan plan;

}
