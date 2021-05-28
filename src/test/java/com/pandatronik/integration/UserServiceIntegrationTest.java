package com.pandatronik.integration;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.user.account.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void testCreateNewUser() throws Exception {
        UserEntity user = createUser();
        assertThat(user)
                .isNotNull();

        assertThat(user.getId())
                .isNotNull();
    }

}
