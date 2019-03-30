package com.pandatronik.backend.persistence.repositories.user.account;

import com.pandatronik.backend.persistence.domain.TokenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TokenRepository extends CrudRepository<TokenEntity, Long> {
    TokenEntity findByToken(String token);

    @Query("select ptr from TokenEntity ptr inner join ptr.user u where ptr.user.id = ?1")
    Set<TokenEntity> findAllByUserId(long userId);
}
