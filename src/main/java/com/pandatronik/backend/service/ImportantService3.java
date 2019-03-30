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
public class ImportantService3 {

    private ImportantRepository3 importantRepository;

    @Autowired
    public ImportantService3(ImportantRepository3 importantRepository) {
        this.importantRepository = importantRepository;
    }

    public Optional<ImportantEntity3> getImportantByUidId(String userProfileId, Long id) {
        return importantRepository.getImportantByUidId(userProfileId, id);
    }

    public Optional<ImportantEntity3> getImportantByUidYearMonthDay(String userProfileId, int year, int month, int day) {
        return importantRepository.getImportantByUidDayMonthYeat(userProfileId, day, month, year);
    }

    public ImportantEntity3 insertImportantRecord(String userProfileId, ImportantEntity3 importantEntity) {
        return importantRepository.save(ImportantEntity3.newImportantRecord(userProfileId, importantEntity));
    }

    public ImportantEntity3 updateImportantRecord(String userProfileId, long id, ImportantEntity3 importantEntity) {

        Optional<ImportantEntity3> optionalImportantEntity = getImportantByUidId(userProfileId, id);
        if (optionalImportantEntity.isPresent()) {
            return importantRepository.save(importantEntity);
        } else {
            throw new NotFoundException("Important record not found");
        }
    }

    public void deleteImportantRecord(String userProfileId, Long id) {
        importantRepository.getImportantByUidId(userProfileId, id).ifPresent(important -> {
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
