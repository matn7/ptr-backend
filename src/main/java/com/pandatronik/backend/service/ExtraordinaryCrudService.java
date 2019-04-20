package com.pandatronik.backend.service;

public interface ExtraordinaryCrudService<T, ID> extends CrudService<T, ID> {

    Iterable<T> findAll(String name);

}
