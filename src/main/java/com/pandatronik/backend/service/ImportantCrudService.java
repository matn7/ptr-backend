package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;

import java.util.List;

public interface ImportantCrudService<T, ID> extends CrudService<T, ID> {

    List<Object[]> findCountByYearStat(UserEntity userEntity, int year);

    List<Object[]> findAverageByYearStat(UserEntity userEntity, int year);
}
