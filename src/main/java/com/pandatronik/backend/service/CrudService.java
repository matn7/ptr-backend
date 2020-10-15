package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;

public interface CrudService<T, ID> {

    T findById(UserEntity userEntity, ID id);

    T findByDate(UserEntity userEntity, int year, int month, int day);

    T save(T object);

    T update(ID id, T object);

    void delete(UserEntity userEntity, ID id);
}
