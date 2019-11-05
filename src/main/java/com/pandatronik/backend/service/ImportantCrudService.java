package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import java.time.LocalDate;
import java.util.List;

public interface ImportantCrudService<T, ID> extends CrudService<T, ID> {

    List<Object[]> findCountByYearStat(UserEntity userEntity, int year);

    List<Object[]> findAverageByYearStat(UserEntity userEntity, int year);

    List<Integer> findCountMadeByStartEnd(UserEntity userEntity, LocalDate startDate, LocalDate endDate);
}
