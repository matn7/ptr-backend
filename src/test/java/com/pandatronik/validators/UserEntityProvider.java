package com.pandatronik.validators;

public class UserEntityProvider {

    public static UserEntityBuilder getValidUserEntity() {
        UserEntityBuilder userEntityBuilder = UserEntityBuilder.defaultUserEntity()
                .withUsername("pandatronik")
                .withPassword("secret123")
                .withEmail("panda@pandatronik.com")
                .withFirstName("Panda")
                .withLastName("Pandatronik");

        return userEntityBuilder;
    }
}
