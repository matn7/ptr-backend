package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity2;
import com.pandatronik.backend.persistence.repositories.ImportantRepository2;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ImportantService2 implements ImportantCrudService<ImportantEntity2, Long> {

    private final ImportantRepository2 importantRepository;
    private final MessageSource messageSource;

    @Override
    public ImportantEntity2 findById(UserEntity userEntity, Long id) {
        return importantRepository.findById(userEntity, id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ImportantEntity2 findByDate(UserEntity userEntity, int year, int month, int day) {
        return importantRepository.findByDate(userEntity, day, month, year).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ImportantEntity2 save(ImportantEntity2 importantEntity) {
        return importantRepository.save(importantEntity);
    }

    @Override
    public ImportantEntity2 update(Long id, ImportantEntity2 importantEntity) {

//        Optional<ImportantEntity2> optionalImportantEntity = findById(userEntity, id);
//        if (optionalImportantEntity.isPresent()) {
//            importantEntity.setUserEntity(userEntity);
            return importantRepository.save(importantEntity);
//        } else {
//            throw new NotFoundException(messageSource.getMessage("important.not.found.message", null
//                    , LocaleContextHolder.getLocale()));
//        }
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

    @Override
    public List<Integer> findCountMadeByStartEnd(UserEntity userEntity, LocalDate startDate, LocalDate endDate) {
        return importantRepository.findCountMadeByStartEnd(userEntity, startDate, endDate);
    }
}
