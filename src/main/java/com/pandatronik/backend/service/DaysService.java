package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.mapper.DaysMapper;
import com.pandatronik.backend.persistence.mapper.EntityMapper;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.model.Important2DTO;
import com.pandatronik.backend.persistence.repositories.DaysRepository;
import com.pandatronik.backend.persistence.repositories.EntityRepository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DaysService extends ResourceService<DaysDTO, DaysEntity, Long> {

    public DaysService(UserService userService,
                       EntityRepository<DaysEntity, Long> entityRepository,
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
