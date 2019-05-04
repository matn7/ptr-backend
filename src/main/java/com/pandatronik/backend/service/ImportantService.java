package com.pandatronik.backend.service;

import com.google.common.collect.Lists;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.repositories.ImportantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class ImportantService implements ImportantCrudService<ImportantEntity, Long> {

    private final ImportantRepository importantRepository;

    @Autowired
    public ImportantService(ImportantRepository importantRepository) {
        this.importantRepository = importantRepository;
    }

    @Override
    public Optional<ImportantEntity> findById(UserEntity userEntity, Long id) {
        return importantRepository.findById(userEntity, id);
    }

    @Override
    public Optional<ImportantEntity> findByDate(UserEntity userEntity, int year, int month, int day) {
        return importantRepository.findByDate(userEntity, day, month, year);
    }

    @Override
    public ImportantEntity save(ImportantEntity importantEntity) {
        return importantRepository.save(importantEntity);
    }

    @Override
    public ImportantEntity update(UserEntity userEntity, Long id, ImportantEntity importantEntity) {

        Optional<ImportantEntity> optionalImportantEntity = findById(userEntity, id);
        if (optionalImportantEntity.isPresent()) {
            importantEntity.setUserEntity(userEntity);
            return importantRepository.save(importantEntity);
        } else {
            throw new NotFoundException("Important record not found");
        }
    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        importantRepository.findById(userEntity, id).ifPresent(important -> {
            importantRepository.delete(important);
        });
    }

    @Override
    public List<Object[]> findCountByYearStat(UserEntity userEntity, int year) {
        return importantRepository.findCountByYearStat(userEntity, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(UserEntity userEntity, int year) {
        return importantRepository.findAverageByYearStat(userEntity, year);
    }
}
