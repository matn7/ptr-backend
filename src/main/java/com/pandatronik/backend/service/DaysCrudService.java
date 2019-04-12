package com.pandatronik.backend.service;

import java.util.List;
import java.util.Optional;

public interface DaysCrudService<T, ID> extends CrudService<T, ID> {

    List<Integer> findByMonthAndYear(String name, int month, int year);

    Integer findByYearAndRateDay(String name, int year, int rate);

    Integer findByMonthYearAndRateDay(String name, int month, int year, int rateDay);

    List<Double> findAverageByYear(String name, int year);

    Optional<List<Integer>> findRateDayData(String name, int year, int month);

}
