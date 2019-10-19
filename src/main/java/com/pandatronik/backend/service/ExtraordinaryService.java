package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.repositories.ExtraordinaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExtraordinaryService implements ExtraordinaryCrudService<ExtraordinaryEntity, Long> {

    private final ExtraordinaryRepository extraordinaryRepository;
    private final MessageSource messageSource;

    @Override
    public Iterable<ExtraordinaryEntity> findAll(UserEntity userEntity) {
        return extraordinaryRepository.findAllByUserEntity(userEntity);
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
            throw new NotFoundException(messageSource.getMessage("extraordinary.not.found.messages", null
                    , LocaleContextHolder.getLocale()));
        }

    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        extraordinaryRepository.findById(userEntity, id).ifPresent(extraordinary -> {
            extraordinaryRepository.delete(extraordinary);
        });
    }

}
