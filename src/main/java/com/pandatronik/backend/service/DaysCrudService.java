package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;

import java.util.List;
import java.util.Optional;

public interface DaysCrudService<T, ID> extends CrudService<T, ID> {

    Optional<List<Integer>> findByMonthAndYearDailyData(UserEntity userEntity, int year, int month);

    List<Integer> findByYearData(UserEntity userEntity, int year);

    List<Object[]> findAverageByYearData(UserEntity userEntity, int year);

    List<Object[]> findByMonthAndYearData(UserEntity userEntity, int month, int year);

}
