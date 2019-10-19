package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity2;
import com.pandatronik.backend.persistence.repositories.LessImportantRepository2;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LessImportantService2 implements ImportantCrudService<LessImportantEntity2, Long> {

    private final LessImportantRepository2 lessImportantRepository2;
    private final MessageSource messageSource;

    @Override
    public Optional<LessImportantEntity2> findById(UserEntity userEntity, Long id) {
        return lessImportantRepository2.findById(userEntity, id);
    }

    @Override
    public Optional<LessImportantEntity2> findByDate(UserEntity userEntity, int year, int month, int day) {
        return lessImportantRepository2.findByDate(userEntity, day, month, year);
    }

    @Override
    public LessImportantEntity2 save(LessImportantEntity2 lessImportantEntity2) {
        return lessImportantRepository2.save(lessImportantEntity2);
    }

    @Override
    public LessImportantEntity2 update(UserEntity userEntity, Long id, LessImportantEntity2 lessImportantEntity2) {

        Optional<LessImportantEntity2> optionalLessImportantEntity = findById(userEntity, id);
        if (optionalLessImportantEntity.isPresent()) {
            lessImportantEntity2.setUserEntity(userEntity);
            return lessImportantRepository2.save(lessImportantEntity2);
        } else {
            throw new NotFoundException(messageSource.getMessage("not.important.not.found.message", null
                    , LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        lessImportantRepository2.findById(userEntity, id).ifPresent(important -> {
            lessImportantRepository2.delete(important);
        });
    }

    @Override
    public List<Object[]> findCountByYearStat(UserEntity userEntity, int year) {
        return lessImportantRepository2.findCountByYearStat(userEntity, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(UserEntity userEntity, int year) {
        return lessImportantRepository2.findAverageByYearStat(userEntity, year);
    }
}
