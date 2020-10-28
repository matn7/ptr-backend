package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
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
    public LessImportantDTO findById(UserEntity userEntity, Long id) {
        return lessImportantRepository.findById(userEntity, id)
                .map(lessImportantMapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public LessImportantDTO findByDate(UserEntity userEntity, int year, int month, int day) {
        return lessImportantRepository.findByDate(userEntity, day, month, year)
                .map(lessImportantMapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<LessImportantDTO> findByDate(UserEntity userEntity, int year, int month) {
        return lessImportantRepository.findByDate(userEntity, year, month)
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
    public void delete(UserEntity userEntity, Long id) {
        lessImportantRepository.deleteById(id);
    }

    @Override
    public List<Object[]> findCountByYearStat(UserEntity userEntity, int year) {
        return lessImportantRepository.findCountByYearStat(userEntity, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(UserEntity userEntity, int year) {
        return lessImportantRepository.findAverageByYearStat(userEntity, year);
    }

    @Override
    public List<Integer> findCountMadeByStartEnd(UserEntity userEntity, LocalDate startDate, LocalDate endDate) {
        return lessImportantRepository.findCountMadeByStartEnd(userEntity, startDate, endDate);
    }

    private LessImportantDTO saveAndReturnDTO(LessImportantEntity lessImportantEntity) {
        LessImportantEntity savedLessImportant = lessImportantRepository.save(lessImportantEntity);
        LessImportantDTO returnDto = lessImportantMapper.lessImportantToLessImportantDTO(savedLessImportant);
        return returnDto;
    }
}
