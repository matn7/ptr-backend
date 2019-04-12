package com.pandatronik.backend.service;

import java.util.List;
import java.util.Optional;

public interface StatisticsDaysService {
    Optional<List<Integer>> findRateDayData(String name, int year, int month);
}
