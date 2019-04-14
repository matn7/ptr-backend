package com.pandatronik.backend.service;

import com.google.common.collect.Lists;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity3;
import com.pandatronik.backend.persistence.repositories.ImportantRepository3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImportantService3 implements ImportantCrudService<ImportantEntity3, Long> {

    private final ImportantRepository3 importantRepository;

    @Autowired
    public ImportantService3(ImportantRepository3 importantRepository) {
        this.importantRepository = importantRepository;
    }

    @Override
    public Optional<ImportantEntity3> findById(String userProfileId, Long id) {
        return importantRepository.findById(userProfileId, id);
    }

    @Override
    public Optional<ImportantEntity3> findByDate(String userProfileId, int year, int month, int day) {
        return importantRepository.findByDate(userProfileId, day, month, year);
    }

    @Override
    public ImportantEntity3 save(String userProfileId, ImportantEntity3 importantEntity) {
        return importantRepository.save(ImportantEntity3.newImportantRecord(userProfileId, importantEntity));
    }

    @Override
    public ImportantEntity3 update(String userProfileId, Long id, ImportantEntity3 importantEntity) {
        Optional<ImportantEntity3> optionalImportantEntity = findById(userProfileId, id);
        if (optionalImportantEntity.isPresent()) {
            return importantRepository.save(importantEntity);
        } else {
            throw new NotFoundException("Important record not found");
        }
    }

    @Override
    public void delete(String userProfileId, Long id) {
        importantRepository.findById(userProfileId, id).ifPresent(important -> {
            importantRepository.delete(important);
        });
    }

    @Override
    public List<Object[]> findCountByYearStat(String userProfileId, int year) {
        return importantRepository.findCountByYearStat(userProfileId, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(String userProfileId, int year) {
        return importantRepository.findAverageByYearStat(userProfileId, year);
    }
}
