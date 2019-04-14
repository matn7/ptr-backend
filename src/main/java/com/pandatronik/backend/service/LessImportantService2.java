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
public class LessImportantService2 implements ImportantCrudService<LessImportantEntity2, Long> {

    private LessImportantRepository2 lessImportantRepository2;

    @Autowired
    public LessImportantService2(LessImportantRepository2 lessImportantRepository2) {
        this.lessImportantRepository2 = lessImportantRepository2;
    }

    @Override
    public Optional<LessImportantEntity2> findById(String userProfileId, Long id) {
        return lessImportantRepository2.findById(userProfileId, id);
    }

    @Override
    public Optional<LessImportantEntity2> findByDate(String userProfileId, int year, int month, int day) {
        return lessImportantRepository2.findByDate(userProfileId, day, month, year);
    }

    @Override
    public LessImportantEntity2 save(String userProfileId, LessImportantEntity2 lessImportantEntity2) {
        return lessImportantRepository2.save(LessImportantEntity2.newLessImportantRecord(userProfileId, lessImportantEntity2));
    }

    @Override
    public LessImportantEntity2 update(String userProfileId, Long id, LessImportantEntity2 lessImportantEntity2) {

        Optional<LessImportantEntity2> optionalLessImportantEntity = findById(userProfileId, id);
        if (optionalLessImportantEntity.isPresent()) {
            return lessImportantRepository2.save(lessImportantEntity2);
        } else {
            throw new NotFoundException("Less important record not found");
        }
    }

    @Override
    public void delete(String userProfileId, Long id) {
        lessImportantRepository2.findById(userProfileId, id).ifPresent(important -> {
            lessImportantRepository2.delete(important);
        });
    }

    @Override
    public List<Object[]> findCountByYearStat(String userProfileId, int year) {
        return lessImportantRepository2.findCountByYearStat(userProfileId, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(String userProfileId, int year) {
        return lessImportantRepository2.findAverageByYearStat(userProfileId, year);
    }
}
