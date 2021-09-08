package com.pandatronik.integration;

import com.pandatronik.PandatronikRestApplication;
import com.pandatronik.backend.persistence.domain.PasswordResetToken;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.service.user.account.PasswordResetTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = PandatronikRestApplication.class)
public class PasswordResetTokenServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Test
    public void testCreateNewTokenForUserEmail() throws Exception {

        UserEntity user = createUser();

        PasswordResetToken passwordResetToken =
                passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());
        assertNotNull(passwordResetToken);
        assertNotNull(passwordResetToken.getToken());

    }

    @Test
    public void testFindByToken() throws Exception {
        UserEntity user = createUser();

        PasswordResetToken passwordResetToken =
                passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());
        assertNotNull(passwordResetToken);
        assertNotNull(passwordResetToken.getToken());

        PasswordResetToken token = passwordResetTokenService.findByToken(passwordResetToken.getToken());
        assertNotNull(token);
    }

}
