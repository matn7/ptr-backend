package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.repositories.LessImportantIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticsLessImportantIndexService implements StatisticsIndexService {
    private LessImportantIndexRepository lessImportantIndexRepository;

    @Autowired
    public StatisticsLessImportantIndexService(LessImportantIndexRepository lessImportantIndexRepository) {
        this.lessImportantIndexRepository = lessImportantIndexRepository;
    }

    @Override
    public Optional<List<Object[]>> findIndexData(UserEntity userEntity, int year, int month) {
        return lessImportantIndexRepository.findIndexData(userEntity, year, month);
    }

}
