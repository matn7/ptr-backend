package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.mapper.LessImportantMapper;
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
        long userId = userEntity.getId();
        return lessImportantRepository.findById(userId, id)
                .map(lessImportantMapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public LessImportantDTO findByDate(String username, int year, int month, int day) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return lessImportantRepository.findByDate(userId, day, month, year)
                .map(lessImportantMapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<LessImportantDTO> findByDate(String username, int year, int month) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return lessImportantRepository.findByDate(userId, year, month)
                .stream()
                .map(lessImportantMapper::lessImportantToLessImportantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LessImportantDTO save(String username, LessImportantDTO lessImportantDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        lessImportantDTO.setUserId(userId);
        LessImportantEntity lessImportant = lessImportantMapper.lessImportantDtoToLessImportant(lessImportantDTO);
        return saveAndReturnDTO(lessImportant);
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
        long userId = userEntity.getId();
        return lessImportantRepository.findCountByYearStat(userId, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(String username, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return lessImportantRepository.findAverageByYearStat(userId, year);
    }

    @Override
    public List<Integer> findCountMadeByStartEnd(String username, LocalDate startDate, LocalDate endDate) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return lessImportantRepository.findCountMadeByStartEnd(userId, startDate, endDate);
    }

    private LessImportantDTO saveAndReturnDTO(LessImportantEntity lessImportantEntity) {
        LessImportantEntity savedLessImportant = lessImportantRepository.save(lessImportantEntity);
        LessImportantDTO returnDto = lessImportantMapper.lessImportantToLessImportantDTO(savedLessImportant);
        return returnDto;
    }
}
