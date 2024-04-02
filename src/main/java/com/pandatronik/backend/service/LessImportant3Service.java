package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportant3Entity;
import com.pandatronik.backend.persistence.mapper.EntityMapper;
import com.pandatronik.backend.persistence.model.TaskDTO;
import com.pandatronik.backend.persistence.repositories.EntityRepository;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.stereotype.Service;

@Service
public class LessImportant3Service extends ResourceService<TaskDTO, LessImportant3Entity> {

    public LessImportant3Service(UserService userService,
                                 EntityRepository<LessImportant3Entity> entityRepository,
                                 EntityMapper<TaskDTO, LessImportant3Entity> entityMapper) {
        super(userService, entityRepository, entityMapper);
    }

    @Override
    public TaskDTO save(String username, TaskDTO importantDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        importantDTO.setUserId(userId);
        LessImportant3Entity important = entityMapper.dtoToEntity(importantDTO);
        important.setUserId(userEntity);
        return saveAndReturnDTO(important);
    }
}
