package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity3;
import com.pandatronik.backend.persistence.repositories.LessImportantRepository3;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LessImportantService3 implements ImportantCrudService<LessImportantEntity3, Long> {

    private final LessImportantRepository3 lessImportantRepository3;
    private final MessageSource messageSource;

    @Override
    public Optional<LessImportantEntity3> findById(UserEntity userEntity, Long id) {
        return lessImportantRepository3.findById(userEntity, id);
    }

    @Override
    public Optional<LessImportantEntity3> findByDate(UserEntity userEntity, int year, int month, int day) {
        return lessImportantRepository3.findByDate(userEntity, day, month, year);
    }

    @Override
    public LessImportantEntity3 save(LessImportantEntity3 lessImportantEntity3) {
        return lessImportantRepository3.save(lessImportantEntity3);
    }

    @Override
    public LessImportantEntity3 update(UserEntity userEntity, Long id, LessImportantEntity3 lessImportantEntity3) {

        Optional<LessImportantEntity3> optionalLessImportantEntity = findById(userEntity, id);
        if (optionalLessImportantEntity.isPresent()) {
            lessImportantEntity3.setUserEntity(userEntity);
            return lessImportantRepository3.save(lessImportantEntity3);
        } else {
            throw new NotFoundException(messageSource.getMessage("not.important.not.found.message", null
                    , LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        lessImportantRepository3.findById(userEntity, id).ifPresent(important -> {
            lessImportantRepository3.delete(important);
        });
    }

    @Override
    public List<Object[]> findCountByYearStat(UserEntity userEntity, int year) {
        return lessImportantRepository3.findCountByYearStat(userEntity, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(UserEntity userEntity, int year) {
        return lessImportantRepository3.findAverageByYearStat(userEntity, year);
    }

    @Override
    public List<Integer> findCountMadeByStartEnd(UserEntity userEntity, LocalDate startDate, LocalDate endDate) {
        return null;
    }
}
