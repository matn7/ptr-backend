package com.pandatronik.backend.service;

import com.google.common.collect.Lists;
import com.pandatronik.backend.persistence.domain.UserEntity;
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
    public Optional<LessImportantEntity> findById(UserEntity userEntity, Long id) {
        return lessImportantRepository.findById(userEntity, id);
    }

    @Override
    public Optional<LessImportantEntity> findByDate(UserEntity userEntity, int year, int month, int day) {
        return lessImportantRepository.findByDate(userEntity, day, month, year);
    }

    @Override
    public LessImportantEntity save(LessImportantEntity lessImportantEntity) {
        return lessImportantRepository.save(lessImportantEntity);
    }

    @Override
    public LessImportantEntity update(UserEntity userEntity, Long id, LessImportantEntity lessImportantEntity) {

        Optional<LessImportantEntity> optionalLessImportantEntity = findById(userEntity, id);
        if (optionalLessImportantEntity.isPresent()) {
            lessImportantEntity.setUserEntity(userEntity);
            return lessImportantRepository.save(lessImportantEntity);
        } else {
            throw new NotFoundException("Less important record not found");
        }
    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        lessImportantRepository.findById(userEntity, id).ifPresent(important -> {
            lessImportantRepository.delete(important);
        });
    }

    @Override
    public List<Object[]> findCountByYearStat(UserEntity userEntity, int year) {
        return lessImportantRepository.findCountByYearStat(userEntity, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(UserEntity userEntity, int year) {
        return lessImportantRepository.findAverageByYearStat(userEntity, year);
    }
}
