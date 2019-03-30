package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.repositories.ExtraordinaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
public class ExtraordinaryService {

    private ExtraordinaryRepository extraordinaryRepository;

    @Autowired
    public ExtraordinaryService(ExtraordinaryRepository extraordinaryRepository) {
        this.extraordinaryRepository = extraordinaryRepository;
    }


    public Iterable<ExtraordinaryEntity> getAll(String username) {
        Iterable<ExtraordinaryEntity> all = extraordinaryRepository.findAllByUserProfileId(username);
        return all;
    }

    public Optional<ExtraordinaryEntity> getExtraordinaryByUidDayMonthYear(String userProfileId, int day, int month, int year) {
        return extraordinaryRepository.getExtraordinaryByUidDayMonthYear(userProfileId, day, month, year);
    }

    public Optional<ExtraordinaryEntity> getExtraordinaryByUidId(String userProfileId, Long id) {
        return extraordinaryRepository.getExtraordinaryByUidId(userProfileId, id);
    }

    public ExtraordinaryEntity updateExtraordinary(ExtraordinaryEntity extraordinaryEntity) {
        Optional<ExtraordinaryEntity> optionalExtraordinaryEntity = getExtraordinaryByUidId(extraordinaryEntity.getUserProfileId(), extraordinaryEntity.getId());
        if (optionalExtraordinaryEntity.isPresent()) {
            return extraordinaryRepository.save(extraordinaryEntity);
        } else {
            throw new NotFoundException("Extraordinary day not found");
        }

    }

    public void removeExtraordinary(String userProfileId, Long id) {
        Long ide = getExtraordinaryByUidId(userProfileId, id)
                .map(ExtraordinaryEntity::getId)
                .orElseThrow(() -> new NotFoundException("Extraordinary day not found"));
        extraordinaryRepository.deleteById(ide);
    }

    public ExtraordinaryEntity insertExtraordinary(String userProfileId, ExtraordinaryEntity extraordinaryEntity) {
        return extraordinaryRepository.save(ExtraordinaryEntity.newExtraordinary(userProfileId, extraordinaryEntity));
    }


}
