package com.pandatronik.backend.service;

import java.time.LocalDate;
import java.util.List;

public interface ImportantCrudService<T, ID> extends CrudService<T, ID> {

    List<Object[]> findCountByYearStat(String username, int year);

    List<Object[]> findAverageByYearStat(String username, int year);

    List<Integer> findCountMadeByStartEnd(String username, LocalDate startDate, LocalDate endDate);
}
