package com.pandatronik.backend.service;

import com.google.common.collect.Lists;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity2;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity3;
import com.pandatronik.backend.persistence.repositories.LessImportantRepository3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class LessImportantService3 implements CrudService<LessImportantEntity3, Long> {

    private LessImportantRepository3 lessImportantRepository3;

    @Autowired
    public LessImportantService3(LessImportantRepository3 lessImportantRepository3) {
        this.lessImportantRepository3 = lessImportantRepository3;
    }

    @Override
    public Optional<LessImportantEntity3> findById(String userProfileId, Long id) {
        return lessImportantRepository3.findById(userProfileId, id);
    }

    @Override
    public Optional<LessImportantEntity3> findByDate(String userProfileId, int year, int month, int day) {
        return lessImportantRepository3.findByDate(userProfileId, day, month, year);
    }

    @Override
    public LessImportantEntity3 save(String userProfileId, LessImportantEntity3 lessImportantEntity3) {
        return lessImportantRepository3.save(LessImportantEntity3.newLessImportantRecord(userProfileId, lessImportantEntity3));
    }

    @Override
    public LessImportantEntity3 update(String userProfileId, Long id, LessImportantEntity3 lessImportantEntity3) {

        Optional<LessImportantEntity3> optionalLessImportantEntity = findById(userProfileId, id);
        if (optionalLessImportantEntity.isPresent()) {
            return lessImportantRepository3.save(lessImportantEntity3);
        } else {
            throw new NotFoundException("Less important record not found");
        }
    }

    @Override
    public void delete(String userProfileId, Long id) {
        lessImportantRepository3.findById(userProfileId, id).ifPresent(important -> {
            lessImportantRepository3.delete(important);
        });
    }

    public List<Integer> countTaskMadeInYear(String userProfileId, int year) {
        List<Integer> result = Lists.newArrayList();
        int important_100 = lessImportantRepository3.countTaskMadeInYear(userProfileId, year, 100);
        int important_75 = lessImportantRepository3.countTaskMadeInYear(userProfileId, year, 75);
        int important_50 = lessImportantRepository3.countTaskMadeInYear(userProfileId, year, 50);
        int important_25 = lessImportantRepository3.countTaskMadeInYear(userProfileId, year, 25);
        int important_0 = lessImportantRepository3.countTaskMadeInYear(userProfileId, year, 0);

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
            monthAvg.add(lessImportantRepository3.findAvgMadeByMonthYear(userProfileId, mon, year));
        });
        return monthAvg;
    }
}
