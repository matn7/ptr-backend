package com.pandatronik.backend.service;

import java.util.List;

public interface ExtraordinaryCrudService<T, ID> extends CrudService<T, ID> {

    List<T> findAll(String username);

}
