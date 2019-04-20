package com.pandatronik.backend.service;

import java.util.List;
import java.util.Optional;

public interface DaysCrudService<T, ID> extends CrudService<T, ID> {

    List<Integer> findByMonthAndYear(String name, int month, int year);

    Integer findByYearAndRateDay(String name, int year, int rate);

    List<Double> findAverageByYear(String name, int year);

    Optional<List<Integer>> findByMonthAndYearDailyData(String name, int year, int month);

    List<Integer> findByYearData(String userProfileId, int year);

    List<Object[]> findAverageByYearData(String userProfileId, int year);

    List<Object[]> findByMonthAndYearData(String name, int month, int year);

}
