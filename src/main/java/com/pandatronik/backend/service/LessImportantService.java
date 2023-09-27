package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.mapper.LessImportantMapper;
import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.persistence.model.LessImportantDTO;
import com.pandatronik.backend.persistence.repositories.LessImportantRepository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessImportantService implements ImportantCrudService<LessImportantDTO, Long>  {

    private final UserService userService;
    private final LessImportantMapper lessImportantMapper;
    private final LessImportantRepository lessImportantRepository;

    @Override
    public LessImportantDTO findById(String username, Long id) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportantRepository.findById(userEntity, id)
                .map(lessImportantMapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public LessImportantDTO findByDate(String username, int year, int month, int day) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportantRepository.findByDate(userEntity, day, month, year)
                .map(lessImportantMapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<LessImportantDTO> findByDate(String username, int year, int month) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportantRepository.findByDate(userEntity, year, month)
                .stream()
                .map(lessImportantMapper::lessImportantToLessImportantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LessImportantDTO save(String username, LessImportantDTO importantDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        importantDTO.setUserId(userId);
        LessImportantEntity important = lessImportantMapper.lessImportantDtoToLessImportant(importantDTO);
        important.setUserId(userEntity);
        return saveAndReturnDTO(important);
    }

    @Override
    public LessImportantDTO update(Long id, LessImportantDTO lessImportantDTO) {
        LessImportantEntity lessImportant = lessImportantMapper.lessImportantDtoToLessImportant(lessImportantDTO);
        lessImportant.setId(id);
        return saveAndReturnDTO(lessImportant);
    }

    @Override
    public void delete(String username, Long id) {
        lessImportantRepository.deleteById(id);
    }

    @Override
    public List<Object[]> findCountByYearStat(String username, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportantRepository.findCountByYearStat(userEntity, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(String username, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportantRepository.findAverageByYearStat(userEntity, year);
    }

    @Override
    public List<Integer> findCountMadeByStartEnd(String username, LocalDate startDate, LocalDate endDate) {
        UserEntity userEntity = userService.findByUserName(username);
        return lessImportantRepository.findCountMadeByStartEnd(userEntity, startDate, endDate);
    }

    private LessImportantDTO saveAndReturnDTO(LessImportantEntity lessImportantEntity) {
        LessImportantEntity savedLessImportant = lessImportantRepository.save(lessImportantEntity);
        LessImportantDTO returnDto = lessImportantMapper.lessImportantToLessImportantDTO(savedLessImportant);
        return returnDto;
    }
}
