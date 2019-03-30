package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.repositories.LessImportantIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessImportantIndexService {
    private LessImportantIndexRepository lessImportantIndexRepository;

    @Autowired
    public LessImportantIndexService(LessImportantIndexRepository lessImportantIndexRepository) {
        this.lessImportantIndexRepository = lessImportantIndexRepository;
    }

    public Optional<List<Object[]>> findLessImportantsIndexData(int year, int month, String name) {
        return lessImportantIndexRepository.findLessImportantIndexData(year, month, name);
    }

}
