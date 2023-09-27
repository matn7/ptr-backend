package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;

import java.util.List;

public interface CrudService<T, ID> {

    T findById(String username, ID id);

    T findByDate(String username, int year, int month, int day);

    List<T> findByDate(String username, int year, int month);

    T save(String username, T object);

    T update(ID id, T object);

    void delete(String username, ID id);
}
