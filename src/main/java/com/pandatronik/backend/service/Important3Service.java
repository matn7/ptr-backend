package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.mapper.Important3Mapper;
import com.pandatronik.backend.persistence.model.Important3DTO;
import com.pandatronik.backend.persistence.repositories.Important3Repository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class Important3Service implements ImportantCrudService<Important3DTO, Long> {

    private final Important3Mapper important3Mapper;
    private final Important3Repository importantRepository;

    @Override
    public Important3DTO findById(long userEntityId, Long id) {
        return importantRepository.findById(userEntityId, id)
                .map(important3Mapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Important3DTO findByDate(long userEntityId, int year, int month, int day) {
        return importantRepository.findByDate(userEntityId, day, month, year)
                .map(important3Mapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Important3DTO duplicateCheck(long userEntityId, int year, int month, int day) {
        return importantRepository.findByDate(userEntityId, day, month, year)
                .map(important3Mapper::importantToImportantDTO).orElse(null);
    }

    @Override
    public List<Important3DTO> findByDate(long userEntityId, int year, int month) {
        return importantRepository.findByDate(userEntityId, year, month)
                .stream()
                .map(important3Mapper::importantToImportantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Important3DTO save(Important3DTO important3DTO) {
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
    public void delete(long userEntityId, Long id) {
        importantRepository.findById(userEntityId, id).ifPresent(important -> {
            importantRepository.delete(important);
        });
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

    private Important3DTO saveAndReturnDTO(Important3Entity important3Entity) {
        Important3Entity savedImportant = importantRepository.save(important3Entity);
        Important3DTO returnDto = important3Mapper.importantToImportantDTO(savedImportant);
        return returnDto;
    }
}
