package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.mapper.Important3Mapper;
import com.pandatronik.backend.persistence.model.Important3DTO;
import com.pandatronik.backend.persistence.repositories.Important3Repository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class Important3Service implements ImportantCrudService<Important3DTO, Long> {

    private final UserService userService;
    private final Important3Mapper important3Mapper;
    private final Important3Repository importantRepository;

    @Override
    public Important3DTO findById(String username, Long id) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return importantRepository.findById(userId, id)
                .map(important3Mapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Important3DTO findByDate(String username, int year, int month, int day) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return importantRepository.findByDate(userId, day, month, year)
                .map(important3Mapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Important3DTO> findByDate(String username, int year, int month) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return importantRepository.findByDate(userId, year, month)
                .stream()
                .map(important3Mapper::importantToImportantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Important3DTO save(String username, Important3DTO important3DTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        important3DTO.setUserId(userId);
        Important3Entity important = important3Mapper.importantDtoToImportant(important3DTO);
        return saveAndReturnDTO(important);
    }

    @Override
    public Important3DTO update(Long id, Important3DTO important3DTO) {
        Important3Entity important = important3Mapper.importantDtoToImportant(important3DTO);
        important.setId(id);
        return saveAndReturnDTO(important);
    }

    @Override
    public void delete(String username, Long id) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        importantRepository.findById(userId, id).ifPresent(importantRepository::delete);
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

    private Important3DTO saveAndReturnDTO(Important3Entity important3Entity) {
        Important3Entity savedImportant = importantRepository.save(important3Entity);
        Important3DTO returnDto = important3Mapper.importantToImportantDTO(savedImportant);
        return returnDto;
    }
}
