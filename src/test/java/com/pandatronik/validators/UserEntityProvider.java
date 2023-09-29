package com.pandatronik.validators;

public class UserEntityProvider {

    public static UserEntityBuilder getValidUserEntity() {
        UserEntityBuilder userEntityBuilder = UserEntityBuilder.defaultUserEntity()
                .withUsername("majka123")
                .withPassword("Secret123!")
                .withEmail("panda@pandatronik.com")
                .withFirstName("Panda")
                .withLastName("Pandatronik");

        return userEntityBuilder;
    }
}
