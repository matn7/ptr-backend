package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.core.LessImportant3Entity;
import com.pandatronik.backend.persistence.mapper.LessImportant3Mapper;
import com.pandatronik.backend.persistence.model.LessImportant3DTO;
import com.pandatronik.backend.persistence.repositories.LessImportant3Repository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessImportant3Service implements ImportantCrudService<LessImportant3DTO, Long> {

    private final LessImportant3Mapper lessImportant3Mapper;
    private final LessImportant3Repository lessImportant3Repository;

    @Override
    public LessImportant3DTO findById(long userEntityId, Long id) {
        return lessImportant3Repository.findById(userEntityId, id)
                .map(lessImportant3Mapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public LessImportant3DTO findByDate(long userEntityId, int year, int month, int day) {
        return lessImportant3Repository.findByDate(userEntityId, day, month, year)
                .map(lessImportant3Mapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public LessImportant3DTO duplicateCheck(long userEntityId, int year, int month, int day) {
        return lessImportant3Repository.findByDate(userEntityId, day, month, year)
                .map(lessImportant3Mapper::lessImportantToLessImportantDTO).orElse(null);
    }

    @Override
    public List<LessImportant3DTO> findByDate(long userEntityId, int year, int month) {
        return lessImportant3Repository.findByDate(userEntityId, year, month)
                .stream()
                .map(lessImportant3Mapper::lessImportantToLessImportantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LessImportant3DTO save(LessImportant3DTO lessImportant3DTO) {
        LessImportant3Entity lessImportant = lessImportant3Mapper.lessImportantDtoToLessImportant(lessImportant3DTO);
        return saveAndReturnDTO(lessImportant);
    }

    @Override
    public LessImportant3DTO update(Long id, LessImportant3DTO lessImportant3DTO) {
        LessImportant3Entity lessImportant = lessImportant3Mapper.lessImportantDtoToLessImportant(lessImportant3DTO);
        lessImportant.setId(id);
        return saveAndReturnDTO(lessImportant);
    }

    @Override
    public void delete(long userEntityId, Long id) {
        lessImportant3Repository.findById(userEntityId, id).ifPresent(important -> {
            lessImportant3Repository.delete(important);
        });
    }

    @Override
    public List<Object[]> findCountByYearStat(long userEntityId, int year) {
        return lessImportant3Repository.findCountByYearStat(userEntityId, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(long userEntityId, int year) {
        return lessImportant3Repository.findAverageByYearStat(userEntityId, year);
    }

    @Override
    public List<Integer> findCountMadeByStartEnd(long userEntityId, LocalDate startDate, LocalDate endDate) {
        return lessImportant3Repository.findCountMadeByStartEnd(userEntityId, startDate, endDate);
    }

    private LessImportant3DTO saveAndReturnDTO(LessImportant3Entity lessImportant3Entity) {
        LessImportant3Entity savedLessImportant = lessImportant3Repository.save(lessImportant3Entity);
        LessImportant3DTO returnDto = lessImportant3Mapper.lessImportantToLessImportantDTO(savedLessImportant);
        return returnDto;
    }
}
