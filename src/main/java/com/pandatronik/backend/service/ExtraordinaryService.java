package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.repositories.ExtraordinaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

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
    public ExtraordinaryEntity findById(UserEntity userEntity, Long id) {
        return extraordinaryRepository.findById(userEntity, id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ExtraordinaryEntity findByDate(UserEntity userEntity, int year, int month, int day) {
        return extraordinaryRepository.findByDate(userEntity, year, month, day).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ExtraordinaryEntity save(ExtraordinaryEntity extraordinaryEntity) {
        return extraordinaryRepository.save(extraordinaryEntity);
    }

    @Override
    public ExtraordinaryEntity update(Long id, ExtraordinaryEntity extraordinaryEntity) {
//        Optional<ExtraordinaryEntity> optionalExtraordinaryEntity = findById(userEntity, id);
//        if (optionalExtraordinaryEntity.isPresent()) {
//            extraordinaryEntity.setUserEntity(userEntity);
            return extraordinaryRepository.save(extraordinaryEntity);
//        } else {
//            throw new NotFoundException(messageSource.getMessage("extraordinary.not.found.messages", null
//                    , LocaleContextHolder.getLocale()));
//        }

    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        extraordinaryRepository.findById(userEntity, id).ifPresent(extraordinary -> {
            extraordinaryRepository.delete(extraordinary);
        });
    }

}
