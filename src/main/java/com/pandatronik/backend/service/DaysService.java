package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.mapper.EntityMapper;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.repositories.EntityRepository;
import com.pandatronik.backend.service.user.account.UserService;
import org.springframework.stereotype.Service;

@Service
public class DaysService extends ResourceService<DaysDTO, DaysEntity> {

    public DaysService(UserService userService,
                       EntityRepository<DaysEntity> entityRepository,
                       EntityMapper<DaysDTO, DaysEntity> entityMapper) {
        super(userService, entityRepository, entityMapper);
    }

    @Override
    public DaysDTO save(String username, DaysDTO daysDTO) {
        UserEntity userEntity = userService.findByUserName(username);
        long userId = userEntity.getId();
        daysDTO.setUserId(userId);
        DaysEntity days = entityMapper.dtoToEntity(daysDTO);
        days.setUserId(userEntity);
        return saveAndReturnDTO(days);
    }

}
