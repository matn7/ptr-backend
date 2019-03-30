package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.repositories.ImportantIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImportantIndexService {
    private ImportantIndexRepository importantIndexRepository;

    @Autowired
    public ImportantIndexService(ImportantIndexRepository importantIndexRepository) {
        this.importantIndexRepository = importantIndexRepository;
    }

    public Optional<List<Object[]>> findImportantsIndexData(int year, int month, String name) {
        return importantIndexRepository.findImportantIndexData(year, month, name);
    }

    public Optional<List<Integer>> findMonthlyDaysRateDay(String name, int year, int month) {
        return importantIndexRepository.findMonthlyDaysRateDay(name, year, month);
    }
}
