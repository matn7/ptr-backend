package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;

import java.util.Optional;

public interface CrudService<T, ID> {

    Optional<T> findById(UserEntity userEntity, ID id);

    Optional<T> findByDate(UserEntity userEntity, int year, int month, int day);

    T save(T object);

    T update(UserEntity userEntity, ID id, T object);

    void delete(UserEntity userEntity, ID id);
}
