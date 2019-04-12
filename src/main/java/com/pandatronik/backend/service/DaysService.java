package com.pandatronik.backend.service;

import com.google.common.collect.Lists;
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
    public Optional<DaysEntity> findByDate(String userProfileId, int day, int month, int year) {
        return daysRepository.findByDate(userProfileId, day, month, year);
    }

    @Override
    public Optional<DaysEntity> findById(String userProfileId, Long id) {
        return daysRepository.findById(userProfileId, id);
    }

    @Override
    public List<Integer> findByMonthAndYear(String userProfileId, int month, int year) {
        return daysRepository.findByMonthAndYear(userProfileId, month, year);
    }

    @Override
    public DaysEntity update(String userProfileId, Long id, DaysEntity daysEntity) {
        Optional<DaysEntity> optionalDaysEntity = findById(userProfileId, id);
        if (optionalDaysEntity.isPresent()) {
            return daysRepository.save(daysEntity);
        }
        throw new NotFoundException("days not found");
    }

    @Override
    public void delete(String userProfileId, Long id) {
        Long ide = findById(userProfileId, id)
                .map(DaysEntity::getId)
                .orElseThrow(() -> new NotFoundException("Day not found"));
        daysRepository.deleteById(ide);
    }

    @Override
    public DaysEntity save(String userProfileId, DaysEntity daysEntity) {
        return daysRepository.save(DaysEntity.newDay(userProfileId, daysEntity));
    }

    @Override
    public Integer findByYearAndRateDay(String userProfileId, int year, int rateDayEnum) {
        return  daysRepository.findByYearAndRateDay(userProfileId, year, rateDayEnum);
    }

    public List<Integer> findByYearAndRateDay2(String userProfileId, int year) {
        return  daysRepository.findByYearAndRateDay2(userProfileId, year);
    }

    @Override
    public Integer findByMonthYearAndRateDay(String name, int month, int year, int rateDay) {
        return daysRepository.findByMonthYearAndRateDay(name, month, year, rateDay);
    }

    @Override
    public List<Double> findAverageByYear(String userProfileId, int year) {
        List<Double> monthAvg = Lists.newArrayList();
        IntStream.rangeClosed(1, 12).forEach(mon -> {
            monthAvg.add(daysRepository.findAverageByYear(userProfileId, mon, year));
        });
        return monthAvg;
    }

    @Override
    public Optional<List<Integer>> findRateDayData(String name, int year, int month) {
        return daysRepository.findRateDayData(name, year, month);
    }
}
