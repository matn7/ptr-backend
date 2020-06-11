package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;

import java.util.List;

public interface IndexDataService<T> {
    List<T> getData(UserEntity userEntity, int year, int month);
}


