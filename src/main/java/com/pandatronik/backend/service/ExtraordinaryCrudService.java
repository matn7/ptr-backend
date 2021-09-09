package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;

import java.util.List;

public interface ExtraordinaryCrudService<T, ID> extends CrudService<T, ID> {

    List<T> findAll(UserEntity userEntity);

}
