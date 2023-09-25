package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.mapper.Important2Mapper;
import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.persistence.repositories.Important2Repository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class Important2Service implements ImportantCrudService<Important2DTO, Long> {

    private final UserService userService;
    private final Important2Mapper important2Mapper;
    private final Important2Repository importantRepository;

    @Override
    public Important2DTO findById(String username, Long id) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return importantRepository.findById(userId, id)
                .map(important2Mapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Important2DTO findByDate(String username, int year, int month, int day) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return importantRepository.findByDate(userId, day, month, year)
                .map(important2Mapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Important2DTO> findByDate(String username, int year, int month) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return importantRepository.findByDate(userId, year, month)
                .stream()
                .map(important2Mapper::importantToImportantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Important2DTO save(String username, Important2DTO important2DTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        important2DTO.setUserId(userId);
        Important2Entity important = important2Mapper.importantDtoToImportant(important2DTO);
        return saveAndReturn(important);
    }

    @Override
    public Important2DTO update(Long id, Important2DTO important2DTO) {
        Important2Entity important = important2Mapper.importantDtoToImportant(important2DTO);
        important.setId(id);
        return saveAndReturn(important);
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

    private Important2DTO saveAndReturn(Important2Entity important2Entity) {
        Important2Entity savedImportant = importantRepository.save(important2Entity);
        Important2DTO returnDto = important2Mapper.importantToImportantDTO(savedImportant);
        return returnDto;
    }
}
