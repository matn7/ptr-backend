package com.pandatronik.backend.service;

import java.util.List;

public interface ImportantCrudService<T, ID> extends CrudService<T, ID> {

    List<Object[]> findCountByYearStat(String userProfileId, int year);

    List<Object[]> findAverageByYearStat(String userProfileId, int year);
}
