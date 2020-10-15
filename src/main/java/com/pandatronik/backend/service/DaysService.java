package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.mapper.DaysMapper;
import com.pandatronik.backend.persistence.model.DaysEntityDTO;
import com.pandatronik.backend.persistence.repositories.DaysRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DaysService implements DaysCrudService<DaysEntityDTO, Long> {

    private final DaysRepository daysRepository;
    private final DaysMapper daysMapper;

    @Override
    public DaysEntityDTO findById(UserEntity userEntity, Long id) {
        return daysRepository.findById(userEntity, id)
                .map(daysMapper::daysToDaysDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public DaysEntityDTO findByDate(UserEntity userEntity, int day, int month, int year) {
        return daysRepository.findByDate(userEntity, day, month, year)
                .map(daysMapper::daysToDaysDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public DaysEntityDTO save(DaysEntityDTO daysEntity) {
        DaysEntity days = daysMapper.daysDtoToDays(daysEntity);
        return saveAndReturnDTO(days);
    }

    @Override
    public DaysEntityDTO update(Long id, DaysEntityDTO daysEntity) {
        DaysEntity days = daysMapper.daysDtoToDays(daysEntity);
        days.setId(id);
        return saveAndReturnDTO(days);
    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        daysRepository.deleteById(id);
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

    private DaysEntityDTO saveAndReturnDTO(DaysEntity daysEntity) {
        DaysEntity savedDays = daysRepository.save(daysEntity);
        DaysEntityDTO returnDto = daysMapper.daysToDaysDTO(savedDays);
        return returnDto;
    }
}
