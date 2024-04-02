package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.mapper.EntityMapper;
import com.pandatronik.backend.persistence.model.TaskDTO;
import com.pandatronik.backend.persistence.repositories.EntityRepository;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.stereotype.Service;

@Service
public class ImportantService extends ResourceService<TaskDTO, ImportantEntity> {


    public ImportantService(UserService userService,
                            EntityRepository<ImportantEntity> entityRepository,
                            EntityMapper<TaskDTO, ImportantEntity> entityMapper) {
        super(userService, entityRepository, entityMapper);
    }

    // Handle UserEntity to Long mapper in Mapstruct
    public TaskDTO save(String username, TaskDTO taskDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        taskDTO.setUserId(userId);
        ImportantEntity important = entityMapper.dtoToEntity(taskDTO);
        important.setUserId(userEntity);
        return saveAndReturnDTO(important);
    }
}
