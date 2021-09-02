package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.mapper.DaysMapper;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.repositories.DaysRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DaysService implements DaysCrudService<DaysDTO, Long> {

    private final DaysRepository daysRepository;
    private final DaysMapper daysMapper;

    @Override
    public DaysDTO findById(long userEntityId, Long id) {
        return daysRepository.findById(userEntityId, id)
                .map(daysMapper::daysToDaysDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<DaysDTO> findByDate(long userEntityId, int year, int month) {
        return daysRepository.findByPartDate(userEntityId, year, month)
                .stream()
                .map(daysMapper::daysToDaysDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DaysDTO findByDate(long userEntityId, int day, int month, int year) {
        return daysRepository.findByDate(userEntityId, day, month, year)
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
    public void delete(long userEntity, Long id) {
        // check whether user can delete other users record known its id
        daysRepository.deleteById(id);
    }

    @Override
    public List<Integer> findByYearData(long userEntityId, int year) {
        return  daysRepository.findByYearData(userEntityId, year);
    }

    @Override
    public List<Object[]> findAverageByYearData(long userEntityId, int year) {
//        return  daysRepository.findAverageByYearData(userEntityId, year);
        return null;
    }

    @Override
    public List<Object[]> findByMonthAndYearData(long userEntityId, int month, int year) {
//        return daysRepository.findByMonthAndYearData(userEntityId, month, year);
        return null;
    }

    @Override
    public Optional<List<Integer>> findByMonthAndYearDailyData(long userEntityId, int year, int month) {
        return null;
//        return daysRepository.findByMonthAndYearDailyData(userEntity, year, month);
    }

    private DaysDTO saveAndReturnDTO(DaysEntity daysEntity) {
        DaysEntity savedDays = daysRepository.save(daysEntity);
        DaysDTO returnDto = daysMapper.daysToDaysDTO(savedDays);
        return returnDto;
    }
}
