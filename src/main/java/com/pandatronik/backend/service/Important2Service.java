package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.mapper.EntityMapper;
import com.pandatronik.backend.persistence.mapper.Important2Mapper;
import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import com.pandatronik.backend.persistence.repositories.EntityRepository;
import com.pandatronik.backend.persistence.repositories.Important2Repository;
import com.pandatronik.backend.service.user.account.CustomUserDetailsService;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Important2Service extends ResourceService<Important2DTO, Important2Entity, Long> {


    public Important2Service(UserService userService,
                             EntityRepository<Important2Entity, Long> entityRepository,
                             EntityMapper<Important2DTO, Important2Entity> entityMapper) {
        super(userService, entityRepository, entityMapper);
    }

    @Override
    public Important2DTO save(String username, Important2DTO importantDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        importantDTO.setUserId(userId);
        Important2Entity important = entityMapper.dtoToEntity(importantDTO);
        important.setUserId(userEntity);
        return saveAndReturnDTO(important);
    }

}
