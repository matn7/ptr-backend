package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.repositories.model.DaysBetween;

import java.util.List;

public interface DaysCrudService<T, ID> extends CrudService<T, ID> {

    List<DaysBetween> findByYearRange(UserEntity userEntity, int yearStart, int yearEnd);

}
