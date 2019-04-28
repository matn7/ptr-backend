package com.pandatronik.backend.service;

import com.google.common.collect.Lists;
import com.pandatronik.backend.persistence.domain.UserEntity;
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
public class LessImportantService3 implements ImportantCrudService<LessImportantEntity3, Long> {

    private LessImportantRepository3 lessImportantRepository3;

    @Autowired
    public LessImportantService3(LessImportantRepository3 lessImportantRepository3) {
        this.lessImportantRepository3 = lessImportantRepository3;
    }

    @Override
    public Optional<LessImportantEntity3> findById(UserEntity userEntity, Long id) {
        return lessImportantRepository3.findById(userEntity, id);
    }

    @Override
    public Optional<LessImportantEntity3> findByDate(UserEntity userEntity, int year, int month, int day) {
        return lessImportantRepository3.findByDate(userEntity, day, month, year);
    }

    @Override
    public LessImportantEntity3 save(LessImportantEntity3 lessImportantEntity3) {
        return lessImportantRepository3.save(lessImportantEntity3);
    }

    @Override
    public LessImportantEntity3 update(UserEntity userEntity, Long id, LessImportantEntity3 lessImportantEntity3) {

        Optional<LessImportantEntity3> optionalLessImportantEntity = findById(userEntity, id);
        if (optionalLessImportantEntity.isPresent()) {
            lessImportantEntity3.setUserEntity(userEntity);
            return lessImportantRepository3.save(lessImportantEntity3);
        } else {
            throw new NotFoundException("Less important record not found");
        }
    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        lessImportantRepository3.findById(userEntity, id).ifPresent(important -> {
            lessImportantRepository3.delete(important);
        });
    }

    @Override
    public List<Object[]> findCountByYearStat(String userProfileId, int year) {
        return lessImportantRepository3.findCountByYearStat(userProfileId, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(String userProfileId, int year) {
        return lessImportantRepository3.findAverageByYearStat(userProfileId, year);
    }
}
