package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;

public interface IndexService<T> {
    T getData(String username, int year, int month);
}
