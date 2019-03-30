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
public class DaysService {

    private DaysRepository daysRepository;

    @Autowired
    public DaysService(DaysRepository daysRepository) {
        this.daysRepository = daysRepository;
    }


    public Iterable<DaysEntity> getAll() {
        Iterable<DaysEntity> all = daysRepository.findAll();
        return all;
    }

    public Optional<DaysEntity> getDaysByUidDayMonthYear(String userProfileId, int day, int month, int year) {
        return daysRepository.getDaysByUidDayMonthYear(userProfileId, day, month, year);
    }

    public Optional<DaysEntity> getDaysByUidId(String userProfileId, Long id) {
        return daysRepository.getDaysByUidId(userProfileId, id);
    }

    public List<Integer> getMonthDaysRateDay(String userProfileId, int month, int year) {
        return daysRepository.getMonthDaysRateDay(userProfileId, month, year);
    }

    public DaysEntity updateDay(String userProfileId, long id, DaysEntity daysEntity) {
        Optional<DaysEntity> optionalDaysEntity = getDaysByUidId(userProfileId, id);
        if (optionalDaysEntity.isPresent()) {
            return daysRepository.save(daysEntity);
        }
        throw new NotFoundException("days not found");
    }

    public void removeDay(String userProfileId, Long id) {
        Long ide = getDaysByUidId(userProfileId, id)
                .map(DaysEntity::getId)
                .orElseThrow(() -> new NotFoundException("Day not found"));
        daysRepository.deleteById(ide);
    }

    public DaysEntity insertDay(String userProfileId, DaysEntity daysEntity) {
        return daysRepository.save(DaysEntity.newDay(userProfileId, daysEntity));
    }


    public Integer findCountRateDayByYearRateDay(String userProfileId, int year, int rateDayEnum) {
        return  daysRepository.findByUserProfileIdYearAndRateDay(userProfileId, year, rateDayEnum);
    }

    public Integer findCountRateDayByMonthYearRateDay(String name, int month, int year, int rateDay) {
        return daysRepository.findCountRateDayByMonthYearRateDay(name, month, year, rateDay);
    }

    public List<Double> avgDayMadeInYear(String userProfileId, int year) {
        List<Double> monthAvg = Lists.newArrayList();
        IntStream.rangeClosed(1, 12).forEach(mon -> {
            monthAvg.add(daysRepository.findAvgDaysByMonthYear(userProfileId, mon, year));
        });
        return monthAvg;
    }

}
