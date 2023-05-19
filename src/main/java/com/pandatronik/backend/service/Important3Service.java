package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.mapper.Important3Mapper;
import com.pandatronik.backend.persistence.model.Important3DTO;
import com.pandatronik.backend.persistence.repositories.Important3Repository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class Important3Service implements ImportantCrudService<Important3DTO, Long> {

    private final Important3Mapper important3Mapper;
    private final Important3Repository importantRepository;

    @Override
    public Important3DTO findById(UserEntity userEntity, Long id) {
        return importantRepository.findById(userEntity, id)
                .map(important3Mapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Important3DTO findByDate(UserEntity userEntity, int year, int month, int day) {
        return importantRepository.findByDate(userEntity, day, month, year)
                .map(important3Mapper::importantToImportantDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Important3DTO duplicateCheck(UserEntity userEntity, int year, int month, int day) {
        return importantRepository.findByDate(userEntity, day, month, year)
                .map(important3Mapper::importantToImportantDTO).orElse(null);
    }

    @Override
    public List<Important3DTO> findByDate(UserEntity userEntity, int year, int month) {
        return importantRepository.findByDate(userEntity, year, month)
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
    public void delete(UserEntity userEntity, Long id) {
        importantRepository.findById(userEntity, id).ifPresent(importantRepository::delete);
    }

    private Important3DTO saveAndReturnDTO(Important3Entity important3Entity) {
        Important3Entity savedImportant = importantRepository.save(important3Entity);
        Important3DTO returnDto = important3Mapper.importantToImportantDTO(savedImportant);
        return returnDto;
    }
}
