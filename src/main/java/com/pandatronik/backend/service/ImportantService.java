package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.mapper.ImportantMapper;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import com.pandatronik.backend.persistence.repositories.ImportantRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImportantService implements ImportantCrudService<ImportantDTO, Long> {

    private final ImportantMapper importantMapper;
    private final ImportantRepository importantRepository;

    @Override
    public ImportantDTO findById(long userEntityId, Long id) {
        return importantRepository.findById(userEntityId, id)
                .map(importantMapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ImportantDTO findByDate(long userEntityId, int year, int month, int day) {
        return importantRepository.findByDate(userEntityId, day, month, year)
                .map(importantMapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ImportantDTO duplicateCheck(long userEntityId, int year, int month, int day) {
        return importantRepository.findByDate(userEntityId, day, month, year)
                .map(importantMapper::importantToImportantDTO).orElse(null);
    }

    @Override
    public List<ImportantDTO> findByDate(long userEntityId, int year, int month) {
        return importantRepository.findByDate(userEntityId, year, month)
                .stream()
                .map(importantMapper::importantToImportantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ImportantDTO save(ImportantDTO importantDTO) {
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
    public void delete(long userEntityId, Long id) {
        importantRepository.deleteById(id);
    }

    @Override
    public List<Object[]> findCountByYearStat(long userEntityId, int year) {
        return importantRepository.findCountByYearStat(userEntityId, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(long userEntityId, int year) {
        return importantRepository.findAverageByYearStat(userEntityId, year);
    }

    @Override
    public List<Integer> findCountMadeByStartEnd(long userEntityId, LocalDate startDate, LocalDate endDate) {
        return importantRepository.findCountMadeByStartEnd(userEntityId, startDate, endDate);
    }

    private ImportantDTO saveAndReturnDTO(ImportantEntity importantEntity) {
        ImportantEntity savedImportant = importantRepository.save(importantEntity);
        ImportantDTO returnDto = importantMapper.importantToImportantDTO(savedImportant);
        return returnDto;
    }
}
