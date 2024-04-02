package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.mapper.EntityMapper;
import com.pandatronik.backend.persistence.model.TaskDTO;
import com.pandatronik.backend.persistence.repositories.EntityRepository;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.stereotype.Service;

@Service
public class Important2Service extends ResourceService<TaskDTO, Important2Entity> {


    public Important2Service(UserService userService,
                             EntityRepository<Important2Entity> entityRepository,
                             EntityMapper<TaskDTO, Important2Entity> entityMapper) {
        super(userService, entityRepository, entityMapper);
    }

    @Override
    public TaskDTO save(String username, TaskDTO taskDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        taskDTO.setUserId(userId);
        Important2Entity important = entityMapper.dtoToEntity(taskDTO);
        important.setUserId(userEntity);
        return saveAndReturnDTO(important);
    }

}
