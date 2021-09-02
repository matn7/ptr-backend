package com.pandatronik.backend.service;

import java.time.LocalDate;
import java.util.List;

public interface ImportantCrudService<T, ID> extends CrudService<T, ID> {

    List<Object[]> findCountByYearStat(long userEntityId, int year);

    List<Object[]> findAverageByYearStat(long userEntityId, int year);

    List<Integer> findCountMadeByStartEnd(long userEntityId, LocalDate startDate, LocalDate endDate);
}
