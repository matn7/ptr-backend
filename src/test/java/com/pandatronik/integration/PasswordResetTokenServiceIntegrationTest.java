package com.pandatronik.integration;

import com.pandatronik.backend.persistence.domain.PasswordResetToken;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.user.account.PasswordResetTokenService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test-mysql.properties")
public class PasswordResetTokenServiceIntegrationTest extends AbstractServiceIntegrationTest {


    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Test
    public void testCreateNewTokenForUserEmail() {

        String randomAlphabetic = RandomStringUtils.randomAlphabetic(10);
        UserEntity user = createUser(randomAlphabetic);

        PasswordResetToken passwordResetToken =
                passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());
        assertNotNull(passwordResetToken);
        assertNotNull(passwordResetToken.getToken());
    }

    @Test
    public void testFindByToken() {
        String randomAlphabetic = RandomStringUtils.randomAlphabetic(10);
        UserEntity user = createUser(randomAlphabetic);

        PasswordResetToken passwordResetToken =
                passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());
        assertNotNull(passwordResetToken);
        assertNotNull(passwordResetToken.getToken());

        PasswordResetToken token = passwordResetTokenService.findByToken(passwordResetToken.getToken());
        assertNotNull(token);

    }
}
