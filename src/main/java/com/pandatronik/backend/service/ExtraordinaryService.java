package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.mapper.ExtraordinaryMapper;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import com.pandatronik.backend.persistence.repositories.ExtraordinaryRepository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExtraordinaryService implements ExtraordinaryCrudService<ExtraordinaryDTO, Long> {

    private final UserService userService;
    private final ExtraordinaryMapper extraordinaryMapper;
    private final ExtraordinaryRepository extraordinaryRepository;

    @Override
    public List<ExtraordinaryDTO> findAll(String username) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        return extraordinaryRepository.findAllByUserId(userId)
                .stream()
                .map(extraordinaryMapper::extraordinaryToExtraordinaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExtraordinaryDTO> findByDate(String username, int year, int month) {
        UserEntity userEntity = userService.findByUserName(username);
        return extraordinaryRepository.findByPartDate(userEntity, year, month)
                .stream()
                .map(extraordinaryMapper::extraordinaryToExtraordinaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExtraordinaryDTO findById(String username, Long id) {
        UserEntity userEntity = userService.findByUserName(username);
        return extraordinaryRepository.findById(userEntity, id)
                .map(extraordinaryMapper::extraordinaryToExtraordinaryDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ExtraordinaryDTO findByDate(String username, int year, int month, int day) {
        UserEntity userEntity = userService.findByUserName(username);
        return extraordinaryRepository.findByDate(userEntity, year, month, day)
                .map(extraordinaryMapper::extraordinaryToExtraordinaryDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ExtraordinaryDTO save(String username, ExtraordinaryDTO extraordinaryDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        extraordinaryDTO.setUserId(userId);
        ExtraordinaryEntity days = extraordinaryMapper.extraordinaryDtoToExtraordinary(extraordinaryDTO);
        days.setUserId(userEntity);
        return saveAndReturnDTO(days);
    }

    @Override
    public ExtraordinaryDTO update(Long id, ExtraordinaryDTO extraordinaryDTO) {
        ExtraordinaryEntity extraordinary = extraordinaryMapper.extraordinaryDtoToExtraordinary(extraordinaryDTO);
        extraordinary.setId(id);
        return saveAndReturnDTO(extraordinary);
    }

    @Override
    public void delete(String username, Long id) {
        extraordinaryRepository.deleteById(id);
    }

    private ExtraordinaryDTO saveAndReturnDTO(ExtraordinaryEntity extraordinaryEntity) {
        ExtraordinaryEntity savedExtraordinary = extraordinaryRepository.save(extraordinaryEntity);
        ExtraordinaryDTO returnDto = extraordinaryMapper.extraordinaryToExtraordinaryDTO(savedExtraordinary);
        return returnDto;
    }

}
