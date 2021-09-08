package com.pandatronik.validators;

public class UserEntityProvider {

    public static UserEntityBuilder getValidUserEntity() {
        UserEntityBuilder userEntityBuilder = UserEntityBuilder.defaultUserEntity()
                .withUsername("testuser")
                .withPassword("Secret123!")
                .withEmail("panda@pandatronik.com")
                .withFirstName("Panda")
                .withLastName("Pandatronik");

        return userEntityBuilder;
    }
}
