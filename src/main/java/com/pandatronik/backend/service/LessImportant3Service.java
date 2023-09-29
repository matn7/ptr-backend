package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportant2Entity;
import com.pandatronik.backend.persistence.domain.core.LessImportant3Entity;
import com.pandatronik.backend.persistence.mapper.EntityMapper;
import com.pandatronik.backend.persistence.mapper.LessImportant3Mapper;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import com.pandatronik.backend.persistence.model.LessImportant2DTO;
import com.pandatronik.backend.persistence.model.LessImportant3DTO;
import com.pandatronik.backend.persistence.repositories.EntityRepository;
import com.pandatronik.backend.persistence.repositories.LessImportant3Repository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessImportant3Service extends ResourceService<LessImportant3DTO, LessImportant3Entity, Long> {

    public LessImportant3Service(UserService userService,
                                 EntityRepository<LessImportant3Entity, Long> entityRepository,
                                 EntityMapper<LessImportant3DTO, LessImportant3Entity> entityMapper) {
        super(userService, entityRepository, entityMapper);
    }

    @Override
    public LessImportant3DTO save(String username, LessImportant3DTO importantDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        importantDTO.setUserId(userId);
        LessImportant3Entity important = entityMapper.dtoToEntity(importantDTO);
        important.setUserId(userEntity);
        return saveAndReturnDTO(important);
    }
}
