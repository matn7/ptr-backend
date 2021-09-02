package com.pandatronik.backend.service;

import java.util.List;

public interface CrudService<T, ID> {

    T findById(long userEntityId, ID id);

    T findByDate(long userEntityId, int year, int month, int day);

    List<T> findByDate(long userEntityId, int year, int month);

    T save(T object);

    T update(ID id, T object);

    void delete(long userEntityId, ID id);
}
