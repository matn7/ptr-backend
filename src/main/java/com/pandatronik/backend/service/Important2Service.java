package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.mapper.Important2Mapper;
import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.persistence.repositories.Important2Repository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class Important2Service implements ImportantCrudService<Important2DTO, Long> {

    private final Important2Mapper important2Mapper;
    private final Important2Repository importantRepository;

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
    public Important2DTO duplicateCheck(UserEntity userEntity, int year, int month, int day) {
        return importantRepository.findByDate(userEntity, day, month, year)
                .map(important2Mapper::importantToImportantDTO).orElse(null);
    }

    @Override
    public List<Important2DTO> findByDate(UserEntity userEntity, int year, int month) {
        return importantRepository.findByDate(userEntity, year, month)
                .stream()
                .map(important2Mapper::importantToImportantDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Important2DTO save(Important2DTO important2DTO) {
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
    public void delete(UserEntity userEntity, Long id) {
        importantRepository.deleteById(id);
    }

    private Important2DTO saveAndReturn(Important2Entity important2Entity) {
        Important2Entity savedImportant = importantRepository.save(important2Entity);
        Important2DTO returnDto = important2Mapper.importantToImportantDTO(savedImportant);
        return returnDto;
    }
}
