package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.repositories.ExtraordinaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
public class ExtraordinaryService implements ExtraordinaryCrudService<ExtraordinaryEntity, Long> {

    private final ExtraordinaryRepository extraordinaryRepository;

    @Autowired
    public ExtraordinaryService(ExtraordinaryRepository extraordinaryRepository) {
        this.extraordinaryRepository = extraordinaryRepository;
    }

    @Override
    public Iterable<ExtraordinaryEntity> findAll(UserEntity userEntity) {
        Iterable<ExtraordinaryEntity> all = extraordinaryRepository.findAllByUserEntity(userEntity);
        return all;
    }

    @Override
    public Optional<ExtraordinaryEntity> findById(UserEntity userEntity, Long id) {
        return extraordinaryRepository.findById(userEntity, id);
    }

    @Override
    public Optional<ExtraordinaryEntity> findByDate(UserEntity userEntity, int year, int month, int day) {
        return extraordinaryRepository.findByDate(userEntity, year, month, day);
    }

    @Override
    public ExtraordinaryEntity save(ExtraordinaryEntity extraordinaryEntity) {
        return extraordinaryRepository.save(extraordinaryEntity);
    }

    @Override
    public ExtraordinaryEntity update(UserEntity userEntity, Long id, ExtraordinaryEntity extraordinaryEntity) {
        Optional<ExtraordinaryEntity> optionalExtraordinaryEntity = findById(userEntity, id);
        if (optionalExtraordinaryEntity.isPresent()) {
            extraordinaryEntity.setUserEntity(userEntity);
            return extraordinaryRepository.save(extraordinaryEntity);
        } else {
            throw new NotFoundException("Extraordinary day not found");
        }

    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        extraordinaryRepository.findById(userEntity, id).ifPresent(extraordinary -> {
            extraordinaryRepository.delete(extraordinary);
        });
    }

}
