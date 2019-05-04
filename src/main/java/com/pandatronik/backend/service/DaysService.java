package com.pandatronik.backend.service;

import com.google.common.collect.Lists;
import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.repositories.DaysRepository;
import com.pandatronik.enums.RateDayEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class DaysService implements DaysCrudService<DaysEntity, Long> {

    private DaysRepository daysRepository;

    @Autowired
    public DaysService(DaysRepository daysRepository) {
        this.daysRepository = daysRepository;
    }

    @Override
    public Optional<DaysEntity> findById(UserEntity userEntity, Long id) {
        return daysRepository.findById(userEntity, id);
    }

    @Override
    public Optional<DaysEntity> findByDate(UserEntity userEntity, int day, int month, int year) {
        return daysRepository.findByDate(userEntity, day, month, year);
    }

    @Override
    public DaysEntity save(DaysEntity daysEntity) {
        return daysRepository.save(daysEntity);
    }

    @Override
    public DaysEntity update(UserEntity userEntity, Long id, DaysEntity daysEntity) {
        Optional<DaysEntity> optionalDaysEntity = findById(userEntity, id);
        if (optionalDaysEntity.isPresent()) {
            daysEntity.setUserEntity(userEntity);
            return daysRepository.save(daysEntity);
        }
        throw new NotFoundException("days not found");
    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        daysRepository.findById(userEntity, id).ifPresent(days -> {
            daysRepository.delete(days);
        });
    }

    @Override
    public List<Integer> findByYearData(UserEntity userEntity, int year) {
        return  daysRepository.findByYearData(userEntity, year);
    }

    @Override
    public List<Object[]> findAverageByYearData(UserEntity userEntity, int year) {
        return  daysRepository.findAverageByYearData(userEntity, year);
    }

    @Override
    public List<Object[]> findByMonthAndYearData(UserEntity userEntity, int month, int year) {
        return daysRepository.findByMonthAndYearData(userEntity, month, year);
    }

    @Override
    public Optional<List<Integer>> findByMonthAndYearDailyData(UserEntity userEntity, int year, int month) {
        return daysRepository.findByMonthAndYearDailyData(userEntity, year, month);
    }
}
