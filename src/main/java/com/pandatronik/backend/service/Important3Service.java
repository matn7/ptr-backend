package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.mapper.EntityMapper;
import com.pandatronik.backend.persistence.mapper.Important3Mapper;
import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.persistence.model.Important3DTO;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import com.pandatronik.backend.persistence.repositories.EntityRepository;
import com.pandatronik.backend.persistence.repositories.Important3Repository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Important3Service extends ResourceService<Important3DTO, Important3Entity, Long> {


    public Important3Service(UserService userService,
                             EntityRepository<Important3Entity, Long> entityRepository,
                             EntityMapper<Important3DTO, Important3Entity> entityMapper) {
        super(userService, entityRepository, entityMapper);
    }

    @Override
    public Important3DTO save(String username, Important3DTO importantDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        importantDTO.setUserId(userId);
        Important3Entity important = entityMapper.dtoToEntity(importantDTO);
        important.setUserId(userEntity);
        return saveAndReturnDTO(important);
    }

    private Important3DTO saveAndReturnDTO(Important3Entity important3Entity) {
        Important3Entity savedImportant = entityRepository.save(important3Entity);
        Important3DTO returnDto = entityMapper.entityToDto(savedImportant);
        return returnDto;
    }
}
