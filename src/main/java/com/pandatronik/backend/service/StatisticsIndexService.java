package com.pandatronik.backend.service;

import java.util.List;
import java.util.Optional;

public interface StatisticsIndexService {

    Optional<List<Object[]>> findIndexData(String name, int year, int month);

}
