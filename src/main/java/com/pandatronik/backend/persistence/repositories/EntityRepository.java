package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;

import java.util.List;
import java.util.Optional;

public interface EntityRepository<Entity, ID> {
    Optional<Entity> findById(UserEntity userId, ID id);

    Optional<Entity> findByDate(UserEntity userId, int day, int month, int year);

    List<Entity> findByDate(UserEntity userId, int year, int month);

    <S extends Entity> S save(S entity);

    void deleteById(ID id);

}
