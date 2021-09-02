package com.pandatronik.backend.service;

public interface IndexService<T> {
    T getData(long userEntityId, int year, int month);
}
