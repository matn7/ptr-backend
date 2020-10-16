package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity3;
import com.pandatronik.backend.persistence.mapper.LessImportant3Mapper;
import com.pandatronik.backend.persistence.model.LessImportant3DTO;
import com.pandatronik.backend.persistence.repositories.LessImportantRepository3;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class LessImportantService3 implements ImportantCrudService<LessImportant3DTO, Long> {

    private final LessImportant3Mapper lessImportant3Mapper;
    private final LessImportantRepository3 lessImportantRepository3;

    @Override
    public LessImportant3DTO findById(UserEntity userEntity, Long id) {
        return lessImportantRepository3.findById(userEntity, id)
                .map(lessImportant3Mapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public LessImportant3DTO findByDate(UserEntity userEntity, int year, int month, int day) {
        return lessImportantRepository3.findByDate(userEntity, day, month, year)
                .map(lessImportant3Mapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public LessImportant3DTO save(LessImportant3DTO lessImportant3DTO) {
        LessImportantEntity3 lessImportant = lessImportant3Mapper.lessImportantDtoToLessImportant(lessImportant3DTO);
        return saveAndReturnDTO(lessImportant);
    }

    @Override
    public LessImportant3DTO update(Long id, LessImportant3DTO lessImportant3DTO) {
        LessImportantEntity3 lessImportant = lessImportant3Mapper.lessImportantDtoToLessImportant(lessImportant3DTO);
        lessImportant.setId(id);
        return saveAndReturnDTO(lessImportant);
    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        lessImportantRepository3.findById(userEntity, id).ifPresent(important -> {
            lessImportantRepository3.delete(important);
        });
    }

    @Override
    public List<Object[]> findCountByYearStat(UserEntity userEntity, int year) {
        return lessImportantRepository3.findCountByYearStat(userEntity, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(UserEntity userEntity, int year) {
        return lessImportantRepository3.findAverageByYearStat(userEntity, year);
    }

    @Override
    public List<Integer> findCountMadeByStartEnd(UserEntity userEntity, LocalDate startDate, LocalDate endDate) {
        return lessImportantRepository3.findCountMadeByStartEnd(userEntity, startDate, endDate);
    }

    private LessImportant3DTO saveAndReturnDTO(LessImportantEntity3 lessImportantEntity3) {
        LessImportantEntity3 savedLessImportant = lessImportantRepository3.save(lessImportantEntity3);
        LessImportant3DTO returnDto = lessImportant3Mapper.lessImportantToLessImportantDTO(savedLessImportant);
        return returnDto;
    }
}
