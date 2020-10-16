package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity2;
import com.pandatronik.backend.persistence.mapper.LessImportant2Mapper;
import com.pandatronik.backend.persistence.model.LessImportant2DTO;
import com.pandatronik.backend.persistence.repositories.LessImportantRepository2;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class LessImportantService2 implements ImportantCrudService<LessImportant2DTO, Long> {

    private final LessImportant2Mapper lessImportant2Mapper;
    private final LessImportantRepository2 lessImportantRepository2;

    @Override
    public LessImportant2DTO findById(UserEntity userEntity, Long id) {
        return lessImportantRepository2.findById(userEntity, id)
                .map(lessImportant2Mapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public LessImportant2DTO findByDate(UserEntity userEntity, int year, int month, int day) {
        return lessImportantRepository2.findByDate(userEntity, day, month, year)
                .map(lessImportant2Mapper::lessImportantToLessImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public LessImportant2DTO save(LessImportant2DTO lessImportant2DTO) {
        LessImportantEntity2 lessImportant = lessImportant2Mapper.lessImportantDtoToLessImportant(lessImportant2DTO);
        return saveAndReturnDTO(lessImportant);
    }

    @Override
    public LessImportant2DTO update(Long id, LessImportant2DTO lessImportant2DTO) {
        LessImportantEntity2 lessImportant = lessImportant2Mapper.lessImportantDtoToLessImportant(lessImportant2DTO);
        lessImportant.setId(id);
        return saveAndReturnDTO(lessImportant);
    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        lessImportantRepository2.findById(userEntity, id).ifPresent(important -> {
            lessImportantRepository2.delete(important);
        });
    }

    @Override
    public List<Object[]> findCountByYearStat(UserEntity userEntity, int year) {
        return lessImportantRepository2.findCountByYearStat(userEntity, year);
    }

    @Override
    public List<Object[]> findAverageByYearStat(UserEntity userEntity, int year) {
        return lessImportantRepository2.findAverageByYearStat(userEntity, year);
    }

    @Override
    public List<Integer> findCountMadeByStartEnd(UserEntity userEntity, LocalDate startDate, LocalDate endDate) {
        return lessImportantRepository2.findCountMadeByStartEnd(userEntity, startDate, endDate);
    }

    private LessImportant2DTO saveAndReturnDTO(LessImportantEntity2 lessImportantEntity2) {
        LessImportantEntity2 savedLessImportant = lessImportantRepository2.save(lessImportantEntity2);
        LessImportant2DTO returnDto = lessImportant2Mapper.lessImportantToLessImportantDTO(savedLessImportant);
        return returnDto;
    }
}
