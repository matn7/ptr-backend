package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.repositories.LessImportantRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LessImportantService implements ImportantCrudService<LessImportantEntity, Long>  {

    private final LessImportantRepository lessImportantRepository;
    private final MessageSource messageSource;

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
            throw new NotFoundException(messageSource.getMessage("not.important.not.found.message", null
                    , LocaleContextHolder.getLocale()));
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
