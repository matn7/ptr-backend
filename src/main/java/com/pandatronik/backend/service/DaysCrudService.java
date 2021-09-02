package com.pandatronik.backend.service;

import java.util.List;
import java.util.Optional;

public interface DaysCrudService<T, ID> extends CrudService<T, ID> {

    Optional<List<Integer>> findByMonthAndYearDailyData(long userEntityId, int year, int month);

    List<Integer> findByYearData(long userEntityId, int year);

    List<Object[]> findAverageByYearData(long userEntityId, int year);

    List<Object[]> findByMonthAndYearData(long userEntityId, int month, int year);

}
