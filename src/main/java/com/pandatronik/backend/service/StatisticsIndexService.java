package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;

import java.util.List;
import java.util.Optional;

public interface StatisticsIndexService {

    Optional<List<Object[]>> findIndexData(UserEntity userEntity, int year, int month);

}
