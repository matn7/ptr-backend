package com.pandatronik.backend.service.user.account;

import com.google.common.base.Preconditions;
import com.pandatronik.backend.persistence.domain.TokenEntity;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.repositories.user.account.TokenRepository;
import com.pandatronik.backend.persistence.repositories.user.account.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Value("${token.expiration.length.minutes}")
    private int tokenExpirationInMinutes;

    private static final Logger LOG = LoggerFactory.getLogger(TokenService.class);

    @Transactional
    public TokenEntity createPasswordResetTokenForEmail(String email) {
        Preconditions.checkNotNull(email, "email must not be null");
        TokenEntity tokenEntity = null;

        UserEntity user = userRepository.findByEmail(email);

        if (null != user) {
            String token = UUID.randomUUID().toString();
            LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
            tokenEntity = new TokenEntity(token, user, now, tokenExpirationInMinutes);

            tokenEntity = tokenRepository.save(tokenEntity);
            LOG.debug("Successfully created token {}  for user {}", token, user.getUsername());
        } else {
            LOG.warn("We couldn't find a user for the given email {}", email);
        }

        return tokenEntity;
    }

    public TokenEntity findByToken(String token) {
        Preconditions.checkNotNull(token, "token must not be null");
        return tokenRepository.findByToken(token);
    }

}
