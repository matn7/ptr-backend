package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.mapper.LessImportantMapper;
import com.pandatronik.backend.persistence.model.LessImportantDTO;
import com.pandatronik.backend.persistence.repositories.LessImportantRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessImportantService implements ImportantCrudService<LessImportantDTO, Long>  {

    private final LessImportantMapper lessImportantMapper;
    private final LessImportantRepository lessImportantRepository;

    @Override
    public LessImportantDTO findById(long userEntityId, Long id) {
        return lessImportantRepository.findById(userEntityId, id)
                .map(lessImportantMapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public LessImportantDTO findByDate(long userEntityId, int year, int month, int day) {
        return lessImportantRepository.findByDate(userEntityId, day, month, year)
                .map(lessImportantMapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<LessImportantDTO> findByDate(long userEntityId, int year, int month) {
        return lessImportantRepository.findByDate(userEntityId, year, month)
                .stream()
                .map(lessImportantMapper::lessImportantToLessImportantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LessImportantDTO save(LessImportantDTO lessImportantDTO) {
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
    public void delete(long userEntityId, Long id) {
        lessImportantRepository.deleteById(id);
    }

    @Override
    public List<Object[]> findCountByYearStat(long userEntityId, int year) {
        return lessImportantRepository.findCountByYearStat(userEntityId, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(long userEntityId, int year) {
        return lessImportantRepository.findAverageByYearStat(userEntityId, year);
    }

    @Override
    public List<Integer> findCountMadeByStartEnd(long userEntityId, LocalDate startDate, LocalDate endDate) {
        return lessImportantRepository.findCountMadeByStartEnd(userEntityId, startDate, endDate);
    }

    private LessImportantDTO saveAndReturnDTO(LessImportantEntity lessImportantEntity) {
        LessImportantEntity savedLessImportant = lessImportantRepository.save(lessImportantEntity);
        LessImportantDTO returnDto = lessImportantMapper.lessImportantToLessImportantDTO(savedLessImportant);
        return returnDto;
    }
}
