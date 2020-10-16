package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.mapper.ExtraordinaryMapper;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import com.pandatronik.backend.persistence.repositories.ExtraordinaryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExtraordinaryService implements ExtraordinaryCrudService<ExtraordinaryDTO, Long> {

    private final ExtraordinaryMapper extraordinaryMapper;
    private final ExtraordinaryRepository extraordinaryRepository;

    @Override
    public List<ExtraordinaryDTO> findAll(UserEntity userEntity) {
        return extraordinaryRepository.findAllByUserEntity(userEntity)
                .stream()
                .map(extraordinaryMapper::extraordinaryToExtraordinaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExtraordinaryDTO findById(UserEntity userEntity, Long id) {
        return extraordinaryRepository.findById(userEntity, id)
                .map(extraordinaryMapper::extraordinaryToExtraordinaryDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ExtraordinaryDTO findByDate(UserEntity userEntity, int year, int month, int day) {
        return extraordinaryRepository.findByDate(userEntity, year, month, day)
                .map(extraordinaryMapper::extraordinaryToExtraordinaryDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }


    @Override
    public ExtraordinaryDTO save(ExtraordinaryDTO extraordinaryDTO) {
        ExtraordinaryEntity extraordinary = extraordinaryMapper.extraordinaryDtoToExtraordinary(extraordinaryDTO);
        return saveAndReturnDTO(extraordinary);
    }

    @Override
    public ExtraordinaryDTO update(Long id, ExtraordinaryDTO extraordinaryDTO) {
        ExtraordinaryEntity extraordinary = extraordinaryMapper.extraordinaryDtoToExtraordinary(extraordinaryDTO);
        extraordinary.setId(id);
        return saveAndReturnDTO(extraordinary);
    }

    @Override
    public void delete(UserEntity userEntity, Long id) {
        extraordinaryRepository.deleteById(id);
    }

    private ExtraordinaryDTO saveAndReturnDTO(ExtraordinaryEntity extraordinaryEntity) {
        ExtraordinaryEntity savedExtraordinary = extraordinaryRepository.save(extraordinaryEntity);
        ExtraordinaryDTO returnDto = extraordinaryMapper.extraordinaryToExtraordinaryDTO(savedExtraordinary);
        return returnDto;
    }

}
