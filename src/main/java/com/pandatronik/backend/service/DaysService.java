package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.mapper.DaysMapper;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.repositories.DaysRepository;
import com.pandatronik.backend.persistence.repositories.model.DaysBetween;
import com.pandatronik.backend.persistence.repositories.model.DaysMonthDateAvgRate;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DaysService implements DaysCrudService<DaysDTO, Long> {

    private final DaysRepository daysRepository;
    private final DaysMapper daysMapper;

    @Override
    public DaysDTO findById(UserEntity userEntity, Long id) {
        return daysRepository.findById(userEntity, id)
                .map(daysMapper::daysToDaysDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<DaysDTO> findByDate(UserEntity userEntity, int year, int month) {
        return daysRepository.findByPartDate(userEntity, year, month)
                .stream()
                .map(daysMapper::daysToDaysDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DaysDTO duplicateCheck(UserEntity userEntity, int year, int month, int day) {
        return daysRepository.findByDate(userEntity, day, month, year)
                .map(daysMapper::daysToDaysDTO).orElse(null);
    }

    @Override
    public DaysDTO findByDate(UserEntity userEntity, int day, int month, int year) {
        System.out.println();
        return daysRepository.findByDate(userEntity, day, month, year)
                .map(daysMapper::daysToDaysDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public DaysDTO save(DaysDTO daysDTO) {
        DaysEntity days = daysMapper.daysDtoToDays(daysDTO);
        return saveAndReturnDTO(days);
    }

    @Override
    public DaysDTO update(Long id, DaysDTO daysDTO) {
        DaysEntity days = daysMapper.daysDtoToDays(daysDTO);
        days.setId(id);
        return saveAndReturnDTO(days);
    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        // check whether user can delete other users record known its id
        daysRepository.deleteById(id);
    }

    @Override
    public List<DaysBetween> findByYearRange(UserEntity userEntity, int yearStart, int yearEnd) {
        List<DaysBetween> byYearRange = daysRepository.findByYearRange(userEntity, yearStart, yearEnd);
        return byYearRange;
    }

    public List<DaysMonthDateAvgRate> findMonthAvgRateDayByYear(UserEntity userEntity, int year) {
        List<DaysMonthDateAvgRate> monthAvgRate = daysRepository.findMonthAvgRateDay(userEntity, year);
        return monthAvgRate;
    }

    private DaysDTO saveAndReturnDTO(DaysEntity daysEntity) {
        DaysEntity savedDays = daysRepository.save(daysEntity);
        DaysDTO returnDto = daysMapper.daysToDaysDTO(savedDays);
        return returnDto;
    }
}
