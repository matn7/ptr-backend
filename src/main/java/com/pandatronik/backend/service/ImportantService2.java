package com.pandatronik.backend.service;

import com.google.common.collect.Lists;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity2;
import com.pandatronik.backend.persistence.repositories.ImportantRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class ImportantService2 implements CrudService<ImportantEntity2, Long> {

    private ImportantRepository2 importantRepository;

    @Autowired
    public ImportantService2(ImportantRepository2 importantRepository) {
        this.importantRepository = importantRepository;
    }

    @Override
    public Optional<ImportantEntity2> findById(String userProfileId, Long id) {
        return importantRepository.findById(userProfileId, id);
    }

    @Override
    public Optional<ImportantEntity2> findByDate(String userProfileId, int year, int month, int day) {
        return importantRepository.findByDate(userProfileId, day, month, year);
    }

    @Override
    public ImportantEntity2 save(String userProfileId, ImportantEntity2 importantEntity) {
        return importantRepository.save(ImportantEntity2.newImportantRecord(userProfileId, importantEntity));
    }

    @Override
    public ImportantEntity2 update(String userProfileId, Long id, ImportantEntity2 importantEntity) {

        Optional<ImportantEntity2> optionalImportantEntity = findById(userProfileId, id);
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

    public List<Integer> countTaskMadeInYear(String userProfileId, int year) {
        List<Integer> result = Lists.newArrayList();
        int important_100 = importantRepository.countTaskMadeInYear(userProfileId, year, 100);
        int important_75 = importantRepository.countTaskMadeInYear(userProfileId, year, 75);
        int important_50 = importantRepository.countTaskMadeInYear(userProfileId, year, 50);
        int important_25 = importantRepository.countTaskMadeInYear(userProfileId, year, 25);
        int important_0 = importantRepository.countTaskMadeInYear(userProfileId, year, 0);

        result.add(important_100);
        result.add(important_75);
        result.add(important_50);
        result.add(important_25);
        result.add(important_0);
        return result;
    }

    public List<Double> avgTaskMadeInMonthYear(String userProfileId, int year) {
        List<Double> monthAvg = Lists.newArrayList();
        for (int month = 1; month <= 12; month++) {
            monthAvg.add(importantRepository.findAvgMadeByMonthYear(userProfileId, month, year));
        }
        return monthAvg;
    }
}
