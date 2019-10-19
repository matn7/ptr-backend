package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.repositories.ImportantRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImportantService implements ImportantCrudService<ImportantEntity, Long> {

    private final ImportantRepository importantRepository;
    private final MessageSource messageSource;

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
            throw new NotFoundException(messageSource.getMessage("important.not.found.message", null
                    , LocaleContextHolder.getLocale()));
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
