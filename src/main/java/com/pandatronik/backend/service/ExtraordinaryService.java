package com.pandatronik.backend.service;

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
    public Iterable<ExtraordinaryEntity> findAll(String username) {
        Iterable<ExtraordinaryEntity> all = extraordinaryRepository.findAllByUserProfileId(username);
        return all;
    }

    @Override
    public Optional<ExtraordinaryEntity> findByDate(String userProfileId, int year, int month, int day) {
        return extraordinaryRepository.findByDate(userProfileId, year, month, day);
    }

    @Override
    public Optional<ExtraordinaryEntity> findById(String userProfileId, Long id) {
        return extraordinaryRepository.findById(userProfileId, id);
    }

    @Override
    public ExtraordinaryEntity update(String userProfileId, Long id, ExtraordinaryEntity extraordinaryEntity) {
        Optional<ExtraordinaryEntity> optionalExtraordinaryEntity = findById(userProfileId, id);
        if (optionalExtraordinaryEntity.isPresent()) {
            return extraordinaryRepository.save(extraordinaryEntity);
        } else {
            throw new NotFoundException("Extraordinary day not found");
        }

    }

    @Override
    public void delete(String userProfileId, Long id) {
        Long ide = findById(userProfileId, id)
                .map(ExtraordinaryEntity::getId)
                .orElseThrow(() -> new NotFoundException("Extraordinary day not found"));
        extraordinaryRepository.deleteById(ide);
    }

    @Override
    public ExtraordinaryEntity save(String userProfileId, ExtraordinaryEntity extraordinaryEntity) {
        return extraordinaryRepository.save(ExtraordinaryEntity.newExtraordinary(userProfileId, extraordinaryEntity));
    }


}
