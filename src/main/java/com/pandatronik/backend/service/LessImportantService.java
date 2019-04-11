package com.pandatronik.backend.service;

import com.google.common.collect.Lists;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity2;
import com.pandatronik.backend.persistence.repositories.LessImportantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class LessImportantService implements CrudService<LessImportantEntity, Long>  {

    private LessImportantRepository lessImportantRepository;

    @Autowired
    public LessImportantService(LessImportantRepository lessImportantRepository) {
        this.lessImportantRepository = lessImportantRepository;
    }

    @Override
    public Optional<LessImportantEntity> findById(String userProfileId, Long id) {
        return lessImportantRepository.findById(userProfileId, id);
    }

    @Override
    public Optional<LessImportantEntity> findByDate(String userProfileId, int year, int month, int day) {
        return lessImportantRepository.findByDate(userProfileId, day, month, year);
    }

    @Override
    public LessImportantEntity save(String userProfileId, LessImportantEntity lessImportantEntity) {
        return lessImportantRepository.save(LessImportantEntity.newLessImportantRecord(userProfileId, lessImportantEntity));
    }

    @Override
    public LessImportantEntity update(String userProfileId, Long id, LessImportantEntity lessImportantEntity) {

        Optional<LessImportantEntity> optionalLessImportantEntity = findById(userProfileId, id);
        if (optionalLessImportantEntity.isPresent()) {
            return lessImportantRepository.save(lessImportantEntity);
        } else {
            throw new NotFoundException("Less important record not found");
        }
    }

    @Override
    public void delete(String userProfileId, Long id) {
        lessImportantRepository.findById(userProfileId, id).ifPresent(important -> {
            lessImportantRepository.delete(important);
        });
    }

    public List<Integer> countTaskMadeInYear(String userProfileId, int year) {
        List<Integer> result = Lists.newArrayList();
        int important_100 = lessImportantRepository.countTaskMadeInYear(userProfileId, year, 100);
        int important_75 = lessImportantRepository.countTaskMadeInYear(userProfileId, year, 75);
        int important_50 = lessImportantRepository.countTaskMadeInYear(userProfileId, year, 50);
        int important_25 = lessImportantRepository.countTaskMadeInYear(userProfileId, year, 25);
        int important_0 = lessImportantRepository.countTaskMadeInYear(userProfileId, year, 0);

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
            monthAvg.add(lessImportantRepository.findAvgMadeByMonthYear(userProfileId, mon, year));
        });
        return monthAvg;
    }
}
