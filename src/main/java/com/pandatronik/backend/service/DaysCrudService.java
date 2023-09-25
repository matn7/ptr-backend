package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;

import java.util.List;
import java.util.Optional;

public interface DaysCrudService<T, ID> extends CrudService<T, ID> {

    Optional<List<Integer>> findByMonthAndYearDailyData(String username, int year, int month);

    List<Integer> findByYearData(String username, int year);

    List<Object[]> findAverageByYearData(String username, int year);

    List<Object[]> findByMonthAndYearData(String username, int month, int year);

}
