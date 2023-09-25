package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.mapper.ImportantMapper;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import com.pandatronik.backend.persistence.repositories.ImportantRepository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImportantService implements ImportantCrudService<ImportantDTO, Long> {

    private final UserService userService;
    private final ImportantMapper importantMapper;
    private final ImportantRepository importantRepository;

    @Override
    public ImportantDTO findById(String username, Long id) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return importantRepository.findById(userId, id)
                .map(importantMapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ImportantDTO findByDate(String username, int year, int month, int day) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return importantRepository.findByDate(userId, day, month, year)
                .map(importantMapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<ImportantDTO> findByDate(String username, int year, int month) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return importantRepository.findByDate(userId, year, month)
                .stream()
                .map(importantMapper::importantToImportantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ImportantDTO save(String username, ImportantDTO importantDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        importantDTO.setUserId(userId);
        ImportantEntity important = importantMapper.importantDtoToImportant(importantDTO);
        return saveAndReturnDTO(important);
    }

    @Override
    public ImportantDTO update(Long id, ImportantDTO importantDTO) {
        ImportantEntity important = importantMapper.importantDtoToImportant(importantDTO);
        important.setId(id);
        return saveAndReturnDTO(important);
    }

    @Override
    public void delete(String username, Long id) {
        importantRepository.deleteById(id);
    }

    @Override
    public List<Object[]> findCountByYearStat(String username, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return importantRepository.findCountByYearStat(userId, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(String username, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return importantRepository.findAverageByYearStat(userId, year);
    }

    @Override
    public List<Integer> findCountMadeByStartEnd(String username, LocalDate startDate, LocalDate endDate) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return importantRepository.findCountMadeByStartEnd(userId, startDate, endDate);
    }

    private ImportantDTO saveAndReturnDTO(ImportantEntity importantEntity) {
        ImportantEntity savedImportant = importantRepository.save(importantEntity);
        ImportantDTO returnDto = importantMapper.importantToImportantDTO(savedImportant);
        return returnDto;
    }
}
