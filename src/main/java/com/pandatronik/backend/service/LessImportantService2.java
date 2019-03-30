package com.pandatronik.backend.service;

import com.google.common.collect.Lists;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity2;
import com.pandatronik.backend.persistence.repositories.LessImportantRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class LessImportantService2 {

    private LessImportantRepository2 lessImportantRepository2;

    @Autowired
    public LessImportantService2(LessImportantRepository2 lessImportantRepository2) {
        this.lessImportantRepository2 = lessImportantRepository2;
    }

    public Optional<LessImportantEntity2> getLessImportantByUidId(String userProfileId, Long id) {
        return lessImportantRepository2.getLessImportantByUidId(userProfileId, id);
    }

    public Optional<LessImportantEntity2> getLessImportantByUidYearMonthDay(String userProfileId, int year, int month, int day) {
        return lessImportantRepository2.getLessImportantByUidDayMonthYeat(userProfileId, day, month, year);
    }

    public LessImportantEntity2 insertLessImportantRecord(String userProfileId, LessImportantEntity2 lessImportantEntity2) {
        return lessImportantRepository2.save(LessImportantEntity2.newLessImportantRecord(userProfileId, lessImportantEntity2));
    }

    public LessImportantEntity2 updateLessImportantRecord(String userProfileId, long id, LessImportantEntity2 lessImportantEntity2) {

        Optional<LessImportantEntity2> optionalLessImportantEntity = getLessImportantByUidId(userProfileId, id);
        if (optionalLessImportantEntity.isPresent()) {
            return lessImportantRepository2.save(lessImportantEntity2);
        } else {
            throw new NotFoundException("Less important record not found");
        }
    }

    public void deleteLessImportantRecord(String userProfileId, Long id) {
        lessImportantRepository2.getLessImportantByUidId(userProfileId, id).ifPresent(important -> {
            lessImportantRepository2.delete(important);
        });
    }

    public List<Integer> countTaskMadeInYear(String userProfileId, int year) {
        List<Integer> result = Lists.newArrayList();
        int important_100 = lessImportantRepository2.countTaskMadeInYear(userProfileId, year, 100);
        int important_75 = lessImportantRepository2.countTaskMadeInYear(userProfileId, year, 75);
        int important_50 = lessImportantRepository2.countTaskMadeInYear(userProfileId, year, 50);
        int important_25 = lessImportantRepository2.countTaskMadeInYear(userProfileId, year, 25);
        int important_0 = lessImportantRepository2.countTaskMadeInYear(userProfileId, year, 0);

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
            monthAvg.add(lessImportantRepository2.findAvgMadeByMonthYear(userProfileId, mon, year));
        });
        return monthAvg;
    }
}
