package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;

public interface ExtraordinaryCrudService<T, ID> extends CrudService<T, ID> {

    Iterable<T> findAll(UserEntity userEntity);

}
