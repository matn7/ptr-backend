package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.mapper.EntityMapper;
import com.pandatronik.backend.persistence.mapper.ExtraordinaryMapper;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import com.pandatronik.backend.persistence.repositories.EntityRepository;
import com.pandatronik.backend.persistence.repositories.ExtraordinaryRepository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtraordinaryService extends ResourceService<ExtraordinaryDTO, ExtraordinaryEntity>  {

    public ExtraordinaryService(UserService userService,
                                EntityRepository<ExtraordinaryEntity> entityRepository,
                                EntityMapper<ExtraordinaryDTO, ExtraordinaryEntity> entityMapper) {
        super(userService, entityRepository, entityMapper);
    }

    @Override
    public ExtraordinaryDTO save(String username, ExtraordinaryDTO extraordinaryDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        extraordinaryDTO.setUserId(userId);
        ExtraordinaryEntity days = entityMapper.dtoToEntity(extraordinaryDTO);
        days.setUserId(userEntity);
        return saveAndReturnDTO(days);
    }
    
}
