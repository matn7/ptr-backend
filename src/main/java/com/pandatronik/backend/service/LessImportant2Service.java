package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportant2Entity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.mapper.LessImportant2Mapper;
import com.pandatronik.backend.persistence.model.LessImportant2DTO;
import com.pandatronik.backend.persistence.model.LessImportantDTO;
import com.pandatronik.backend.persistence.repositories.LessImportant2Repository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessImportant2Service implements ImportantCrudService<LessImportant2DTO, Long> {

    private final UserService userService;
    private final LessImportant2Mapper lessImportant2Mapper;
    private final LessImportant2Repository lessImportant2Repository;

    @Override
    public LessImportant2DTO findById(String username, Long id) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportant2Repository.findById(userEntity, id)
                .map(lessImportant2Mapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public LessImportant2DTO findByDate(String username, int year, int month, int day) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportant2Repository.findByDate(userEntity, day, month, year)
                .map(lessImportant2Mapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<LessImportant2DTO> findByDate(String username, int year, int month) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportant2Repository.findByDate(userEntity, year, month)
                .stream()
                .map(lessImportant2Mapper::lessImportantToLessImportantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LessImportant2DTO save(String username, LessImportant2DTO importantDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        importantDTO.setUserId(userId);
        LessImportant2Entity important = lessImportant2Mapper.lessImportantDtoToLessImportant(importantDTO);
        important.setUserId(userEntity);
        return saveAndReturnDTO(important);
    }

    @Override
    public LessImportant2DTO update(Long id, LessImportant2DTO lessImportant2DTO) {
        LessImportant2Entity lessImportant = lessImportant2Mapper.lessImportantDtoToLessImportant(lessImportant2DTO);
        lessImportant.setId(id);
        return saveAndReturnDTO(lessImportant);
    }

    @Override
    public void delete(String username, Long id) {
        UserEntity userEntity = userService.findByUserName(username);
        lessImportant2Repository.findById(userEntity, id).ifPresent(lessImportant2Repository::delete);
    }

    @Override
    public List<Object[]> findCountByYearStat(String username, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportant2Repository.findCountByYearStat(userEntity, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(String username, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportant2Repository.findAverageByYearStat(userEntity, year);
    }

    @Override
    public List<Integer> findCountMadeByStartEnd(String username, LocalDate startDate, LocalDate endDate) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportant2Repository.findCountMadeByStartEnd(userEntity, startDate, endDate);
    }

    private LessImportant2DTO saveAndReturnDTO(LessImportant2Entity lessImportant2Entity) {
        LessImportant2Entity savedLessImportant = lessImportant2Repository.save(lessImportant2Entity);
        LessImportant2DTO returnDto = lessImportant2Mapper.lessImportantToLessImportantDTO(savedLessImportant);
        return returnDto;
    }
}
