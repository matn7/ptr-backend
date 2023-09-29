package com.pandatronik.integration;

import com.pandatronik.PandatronikRestApplication;
import com.pandatronik.backend.persistence.domain.PasswordResetToken;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.repositories.user.account.PasswordResetTokenRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PandatronikRestApplication.class)
public class PasswordResetTokenIntegrationTest extends AbstractIntegrationTest {
    @Value("${token.expiration.length.minutes}")
    private int expirationTimeInMinutes;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Test
    public void tokenExpirationLength() throws Exception {
        String generatedString = RandomStringUtils.randomAlphabetic(10);

        UserEntity user = createUser(generatedString);
        assertNotNull(user);
        assertNotNull(user.getId());

        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        String token = UUID.randomUUID().toString();

        LocalDateTime expectedTime = now.plusMinutes(expirationTimeInMinutes);

        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, now);

        LocalDateTime actualTime = passwordResetToken.getExpiryDate();
        assertNotNull(actualTime);
        assertEquals(expectedTime, actualTime);
    }

    @Test
    public void testFindTokenByTokenValue() throws Exception {
        String generatedString = RandomStringUtils.randomAlphabetic(10);

        UserEntity user = createUser(generatedString);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        createPasswordResetToken(token, user, now);

        PasswordResetToken retrievedPasswordResetToken = passwordResetTokenRepository.findByToken(token);
        assertNotNull(retrievedPasswordResetToken);
        assertNotNull(retrievedPasswordResetToken.getId());
        assertNotNull(retrievedPasswordResetToken.getUser());
    }

    @Test
    public void testDeleteToken() throws Exception {
        String generatedString = RandomStringUtils.randomAlphabetic(10);

        UserEntity user = createUser(generatedString);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, now);
        long tokenId = passwordResetToken.getId();
        passwordResetTokenRepository.deleteById(tokenId);

        PasswordResetToken shouldNotExistToken = passwordResetTokenRepository.findById(tokenId).get();
        assertNull(shouldNotExistToken);
    }

    @Test
    public void testCascadeDeleteFromUserEntity() throws Exception {
        // to generate random jest zle bo musi byc
        String generatedString = RandomStringUtils.randomAlphabetic(10);
        UserEntity user = createUser(generatedString);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, now);
        passwordResetToken.getId();

        userRepository.deleteById(user.getId());

        Set<PasswordResetToken> shouldBeEmpty = passwordResetTokenRepository.findAllByUserId(user.getId());
        assertTrue(shouldBeEmpty.isEmpty());


    }
    @Test
    public void testMultipleTokensAreReturnedWhenQueringByUserId() throws Exception {
        String generatedString = RandomStringUtils.randomAlphabetic(10);

        UserEntity user = createUser(generatedString);
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        String token1 = UUID.randomUUID().toString();
        String token2 = UUID.randomUUID().toString();
        String token3 = UUID.randomUUID().toString();

        Set<PasswordResetToken> tokens = new HashSet<>();
        tokens.add(createPasswordResetToken(token1, user, now));
        tokens.add(createPasswordResetToken(token2, user, now));
        tokens.add(createPasswordResetToken(token3, user, now));

//        passwordResetTokenRepository.save(tokens);
        passwordResetTokenRepository.saveAll(tokens);

        UserEntity foundUser = userRepository.findById(user.getId()).get();

        Set<PasswordResetToken> actualTokens = passwordResetTokenRepository.findAllByUserId(foundUser.getId());
        assertTrue(actualTokens.size() == tokens.size());
        List<String> tokensAsList = tokens.stream().map(prt -> prt.getToken()).collect(Collectors.toList());
        List<String> actualTokensAsList = actualTokens.stream().map(prt -> prt.getToken()).collect(Collectors.toList());
        assertEquals(tokensAsList, actualTokensAsList);

    }

    private PasswordResetToken createPasswordResetToken(String token, UserEntity user, LocalDateTime now) {

        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, now, expirationTimeInMinutes);
        passwordResetTokenRepository.save(passwordResetToken);
        assertNotNull(passwordResetToken.getId());
        return passwordResetToken;
    }
}
