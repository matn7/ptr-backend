package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportant2Entity;
import com.pandatronik.backend.persistence.domain.core.LessImportant3Entity;
import com.pandatronik.backend.persistence.mapper.LessImportant3Mapper;
import com.pandatronik.backend.persistence.model.LessImportant2DTO;
import com.pandatronik.backend.persistence.model.LessImportant3DTO;
import com.pandatronik.backend.persistence.repositories.LessImportant3Repository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessImportant3Service implements ImportantCrudService<LessImportant3DTO, Long> {

    private final UserService userService;
    private final LessImportant3Mapper lessImportant3Mapper;
    private final LessImportant3Repository lessImportant3Repository;

    @Override
    public LessImportant3DTO findById(String username, Long id) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportant3Repository.findById(userEntity, id)
                .map(lessImportant3Mapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public LessImportant3DTO findByDate(String username, int year, int month, int day) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportant3Repository.findByDate(userEntity, day, month, year)
                .map(lessImportant3Mapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<LessImportant3DTO> findByDate(String username, int year, int month) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportant3Repository.findByDate(userEntity, year, month)
                .stream()
                .map(lessImportant3Mapper::lessImportantToLessImportantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LessImportant3DTO save(String username, LessImportant3DTO importantDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        importantDTO.setUserId(userId);
        LessImportant3Entity important = lessImportant3Mapper.lessImportantDtoToLessImportant(importantDTO);
        important.setUserId(userEntity);
        return saveAndReturnDTO(important);
    }


    @Override
    public LessImportant3DTO update(Long id, LessImportant3DTO lessImportant3DTO) {
        LessImportant3Entity lessImportant = lessImportant3Mapper.lessImportantDtoToLessImportant(lessImportant3DTO);
        lessImportant.setId(id);
        return saveAndReturnDTO(lessImportant);
    }

    @Override
    public void delete(String username, Long id) {
        UserEntity userEntity = userService.findByUserName(username);
        lessImportant3Repository.findById(userEntity, id).ifPresent(lessImportant3Repository::delete);
    }

    @Override
    public List<Object[]> findCountByYearStat(String username, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportant3Repository.findCountByYearStat(userEntity, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(String username, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportant3Repository.findAverageByYearStat(userEntity, year);
    }

    @Override
    public List<Integer> findCountMadeByStartEnd(String username, LocalDate startDate, LocalDate endDate) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportant3Repository.findCountMadeByStartEnd(userEntity, startDate, endDate);
    }

    private LessImportant3DTO saveAndReturnDTO(LessImportant3Entity lessImportant3Entity) {
        LessImportant3Entity savedLessImportant = lessImportant3Repository.save(lessImportant3Entity);
        LessImportant3DTO returnDto = lessImportant3Mapper.lessImportantToLessImportantDTO(savedLessImportant);
        return returnDto;
    }
}
