package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.mapper.DaysMapper;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.repositories.DaysRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public DaysDTO findByDate(UserEntity userEntity, int day, int month, int year) {
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

    private DaysDTO saveAndReturnDTO(DaysEntity daysEntity) {
        DaysEntity savedDays = daysRepository.save(daysEntity);
        DaysDTO returnDto = daysMapper.daysToDaysDTO(savedDays);
        return returnDto;
    }
}
