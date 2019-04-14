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
public class LessImportantService implements ImportantCrudService<LessImportantEntity, Long>  {

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

    @Override
    public List<Object[]> findCountByYearStat(String userProfileId, int year) {
        return lessImportantRepository.findCountByYearStat(userProfileId, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(String userProfileId, int year) {
        return lessImportantRepository.findAverageByYearStat(userProfileId, year);
    }
}
