package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportant2Entity;
import com.pandatronik.backend.persistence.mapper.EntityMapper;
import com.pandatronik.backend.persistence.model.TaskDTO;
import com.pandatronik.backend.persistence.repositories.EntityRepository;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.stereotype.Service;

@Service
public class LessImportant2Service extends ResourceService<TaskDTO, LessImportant2Entity> {


    public LessImportant2Service(UserService userService,
                                 EntityRepository<LessImportant2Entity> entityRepository,
                                 EntityMapper<TaskDTO, LessImportant2Entity> entityMapper) {
        super(userService, entityRepository, entityMapper);
    }

    public TaskDTO save(String username, TaskDTO importantDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        importantDTO.setUserId(userId);
        LessImportant2Entity important = entityMapper.dtoToEntity(importantDTO);
        important.setUserId(userEntity);
        return saveAndReturnDTO(important);
    }
}
