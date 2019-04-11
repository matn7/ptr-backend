package com.pandatronik.backend.service;

import com.google.common.collect.Lists;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.repositories.ImportantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class ImportantService implements CrudService<ImportantEntity, Long> {

    private ImportantRepository importantRepository;

    @Autowired
    public ImportantService(ImportantRepository importantRepository) {
        this.importantRepository = importantRepository;
    }

    @Override
    public Optional<ImportantEntity> findById(String userProfileId, Long id) {
        return importantRepository.findById(userProfileId, id);
    }

    @Override
    public Optional<ImportantEntity> findByDate(String userProfileId, int year, int month, int day) {
        return importantRepository.findByDate(userProfileId, day, month, year);
    }

    @Override
    public ImportantEntity save(String userProfileId, ImportantEntity importantEntity) {
        return importantRepository.save(ImportantEntity.newImportantRecord(userProfileId, importantEntity));
    }

    @Override
    public ImportantEntity update(String userProfileId, Long id, ImportantEntity importantEntity) {

        Optional<ImportantEntity> optionalImportantEntity = findById(userProfileId, id);
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
        IntStream.rangeClosed(1, 12).forEach(mon -> {
            monthAvg.add(importantRepository.findAvgMadeByMonthYear(userProfileId, mon, year));
        });
        return monthAvg;
    }
}
