package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.mapper.EntityMapper;
import com.pandatronik.backend.persistence.model.TaskDTO;
import com.pandatronik.backend.persistence.repositories.EntityRepository;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.stereotype.Service;

@Service
public class LessImportantService extends ResourceService<TaskDTO, LessImportantEntity>  {

    public LessImportantService(UserService userService,
                                EntityRepository<LessImportantEntity> entityRepository,
                                EntityMapper<TaskDTO, LessImportantEntity> entityMapper) {
        super(userService, entityRepository, entityMapper);
    }

    @Override
    public TaskDTO save(String username, TaskDTO importantDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        importantDTO.setUserId(userId);
        LessImportantEntity important = entityMapper.dtoToEntity(importantDTO);
        important.setUserId(userEntity);
        return saveAndReturnDTO(important);
    }

}
