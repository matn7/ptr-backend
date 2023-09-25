package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.mapper.DaysMapper;
import com.pandatronik.backend.persistence.mapper.DaysMapperV2;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.repositories.DaysRepository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DaysService implements DaysCrudService<DaysDTO, Long> {

    private final UserService userService;
    private final DaysRepository daysRepository;
    private final DaysMapper daysMapper;

    @Override
    public DaysDTO findById(String username, Long id) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return daysRepository.findById(userId, id)
                .map(daysMapper::daysToDaysDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<DaysDTO> findByDate(String username, int year, int month) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return daysRepository.findByPartDate(userId, year, month)
                .stream()
                .map(daysMapper::daysToDaysDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DaysDTO findByDate(String username, int day, int month, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return daysRepository.findByDate(userId, day, month, year)
                .map(daysMapper::daysToDaysDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public DaysDTO save(String username, DaysDTO daysDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        daysDTO.setUserId(userId);
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
    public void delete(String username, Long id) {
        // check whether user can delete other users record known its id
        daysRepository.deleteById(id);
    }

    @Override
    public List<Integer> findByYearData(String username, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return  daysRepository.findByYearData(userId, year);
    }

    @Override
    public List<Object[]> findAverageByYearData(String username, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return  daysRepository.findAverageByYearData(userId, year);
    }

    @Override
    public List<Object[]> findByMonthAndYearData(String username, int month, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return daysRepository.findByMonthAndYearData(userId, month, year);
    }

    @Override
    public Optional<List<Integer>> findByMonthAndYearDailyData(String username, int year, int month) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return daysRepository.findByMonthAndYearDailyData(userId, year, month);
    }

    private DaysDTO saveAndReturnDTO(DaysEntity daysEntity) {
        DaysEntity savedDays = daysRepository.save(daysEntity);
        DaysDTO returnDto = daysMapper.daysToDaysDTO(savedDays);
        return returnDto;
    }
}
