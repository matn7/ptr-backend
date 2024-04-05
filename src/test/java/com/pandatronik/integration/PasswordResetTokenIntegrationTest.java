package com.pandatronik.integration;

import com.pandatronik.PandatronikRestApplication;
import com.pandatronik.backend.persistence.domain.PasswordResetToken;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.repositories.user.account.PasswordResetTokenRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test-mysql.properties")
public class PasswordResetTokenIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Value("${token.expiration.length.minutes}")
    private int expirationTimeInMinutes;

    @Value("${sql.script.disable.fk.check}")
    private String disableForeignKeyCheck;

    @Value("${sql.script.delete.userEntity}")
    private String deleteUser;

    @Value("${sql.script.delete.password.reset.token}")
    private String deletePasswordResetToken;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @BeforeEach
    public void setup() {
        jdbc.execute(disableForeignKeyCheck);
        jdbc.execute(deleteUser);
        jdbc.execute(deletePasswordResetToken);
    }

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

    @Transactional
    @Test
    public void testCascadeDeleteFromUserEntity() throws Exception {
        String generatedString = RandomStringUtils.randomAlphabetic(10);
        UserEntity user = createUser(generatedString);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, now);
        long tokenId = passwordResetToken.getId();

        Optional<PasswordResetToken> tokenByIdPresent = passwordResetTokenRepository.findById(tokenId);
        assertTrue(tokenByIdPresent.isPresent());

        userRepository.deleteById(user.getId());

        Set<PasswordResetToken> shouldBeEmpty = passwordResetTokenRepository.findAllByUserId(user.getId());
        assertTrue(shouldBeEmpty.isEmpty());
    }

    @Transactional
    @Test
    public void testDeleteToken() throws Exception {
        String generatedString = RandomStringUtils.randomAlphabetic(10);

        UserEntity user = createUser(generatedString);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        PasswordResetToken passwordResetToken = createPasswordResetToken(token, user, now);

        long tokenId = passwordResetToken.getId();

        Optional<PasswordResetToken> tokenByIdPresent = passwordResetTokenRepository.findById(tokenId);
        assertTrue(tokenByIdPresent.isPresent());

        passwordResetTokenRepository.deleteById(tokenId);

        Optional<PasswordResetToken> tokenByIdEmpty = passwordResetTokenRepository.findById(tokenId);
        assertTrue(tokenByIdEmpty.isEmpty());
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

        Optional<UserEntity> userById = userRepository.findById(user.getId());
        assertTrue(userById.isPresent());

        UserEntity foundUser = userById.get();

        Set<PasswordResetToken> actualTokens = passwordResetTokenRepository.findAllByUserId(foundUser.getId());
        assertEquals(actualTokens.size(), tokens.size());
        List<String> tokensAsList = tokens.stream().map(PasswordResetToken::getToken).toList();
        List<String> actualTokensAsList = actualTokens.stream().map(PasswordResetToken::getToken).toList();
        assertEquals(tokensAsList.size(), actualTokensAsList.size());
//        assertThat(tokensAsList, containsInAnyOrder(actualTokensAsList));
    }

    private PasswordResetToken createPasswordResetToken(String token, UserEntity user, LocalDateTime now) {

        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, now, expirationTimeInMinutes);
        passwordResetTokenRepository.save(passwordResetToken);
        return passwordResetToken;
    }
}
