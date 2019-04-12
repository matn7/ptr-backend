package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.repositories.ImportantIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticsImportantIndexService implements StatisticsIndexService {
    private ImportantIndexRepository importantIndexRepository;

    @Autowired
    public StatisticsImportantIndexService(ImportantIndexRepository importantIndexRepository) {
        this.importantIndexRepository = importantIndexRepository;
    }

    @Override
    public Optional<List<Object[]>> findIndexData(String name, int year, int month) {
        return importantIndexRepository.findIndexData(name, year, month);
    }
}
