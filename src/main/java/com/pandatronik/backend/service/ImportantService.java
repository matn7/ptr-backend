package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.mapper.EntityMapper;
import com.pandatronik.backend.persistence.mapper.ImportantMapper;
import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import com.pandatronik.backend.persistence.repositories.EntityRepository;
import com.pandatronik.backend.persistence.repositories.ImportantRepository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImportantService extends ResourceService<ImportantDTO, ImportantEntity, Long> {


    public ImportantService(UserService userService,
                            EntityRepository<ImportantEntity, Long> entityRepository,
                            EntityMapper<ImportantDTO, ImportantEntity> entityMapper) {
        super(userService, entityRepository, entityMapper);
    }

    // Handle UserEntity to Long mapper in Mapstruct
    public ImportantDTO save(String username, ImportantDTO importantDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        importantDTO.setUserId(userId);
        ImportantEntity important = entityMapper.dtoToEntity(importantDTO);
        important.setUserId(userEntity);
        return saveAndReturnDTO(important);
    }
}
