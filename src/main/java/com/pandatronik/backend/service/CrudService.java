package com.pandatronik.backend.service;

import java.util.Optional;

public interface CrudService<T, ID> {

    Optional<T> findById(String name, ID id);

    Optional<T> findByDate(String name, int year, int month, int day);

    T save(String name, T object);

    T update(String name, ID id, T object);

    void delete(String name, ID id);
}
