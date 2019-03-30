package com.pandatronik.validators;

import com.pandatronik.backend.persistence.domain.UserEntity;

public class UserEntityBuilder {
    UserEntity userEntity = new UserEntity();

    public static UserEntityBuilder defaultUserEntity() {
        return new UserEntityBuilder();
    }

    public UserEntityBuilder withUsername(String username) {
        this.userEntity.setUsername(username);
        return this;
    }

    public UserEntityBuilder withPassword(String password) {
        this.userEntity.setPassword(password);
        return this;
    }

    public UserEntityBuilder withEmail(String email) {
        this.userEntity.setEmail(email);
        return this;
    }

    public UserEntityBuilder withFirstName(String firstName) {
        this.userEntity.setFirstName(firstName);
        return this;
    }

    public UserEntityBuilder withLastName(String lastName) {
        this.userEntity.setLastName(lastName);
        return this;
    }

    public UserEntity build() {
        return userEntity;
    }

}
