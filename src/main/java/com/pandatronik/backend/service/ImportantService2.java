package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity2;
import com.pandatronik.backend.persistence.mapper.Important2Mapper;
import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.persistence.repositories.ImportantRepository2;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ImportantService2 implements ImportantCrudService<Important2DTO, Long> {

    private final Important2Mapper important2Mapper;
    private final ImportantRepository2 importantRepository;

    @Override
    public Important2DTO findById(UserEntity userEntity, Long id) {
        return importantRepository.findById(userEntity, id)
                .map(important2Mapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Important2DTO findByDate(UserEntity userEntity, int year, int month, int day) {
        return importantRepository.findByDate(userEntity, day, month, year)
                .map(important2Mapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Important2DTO save(Important2DTO important2DTO) {
        ImportantEntity2 important = important2Mapper.importantDtoToImportant(important2DTO);
        return saveAndReturn(important);
    }

    @Override
    public Important2DTO update(Long id, Important2DTO important2DTO) {
        ImportantEntity2 important = important2Mapper.importantDtoToImportant(important2DTO);
        important.setId(id);
        return saveAndReturn(important);
    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        importantRepository.deleteById(id);
    }

    @Override
    public List<Object[]> findCountByYearStat(UserEntity userEntity, int year) {
        return importantRepository.findCountByYearStat(userEntity, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(UserEntity userEntity, int year) {
        return importantRepository.findAverageByYearStat(userEntity, year);
    }

    @Override
    public List<Integer> findCountMadeByStartEnd(UserEntity userEntity, LocalDate startDate, LocalDate endDate) {
        return importantRepository.findCountMadeByStartEnd(userEntity, startDate, endDate);
    }

    private Important2DTO saveAndReturn(ImportantEntity2 importantEntity2) {
        ImportantEntity2 savedImportant = importantRepository.save(importantEntity2);
        Important2DTO returnDto = important2Mapper.importantToImportantDTO(savedImportant);
        return returnDto;
    }
}
