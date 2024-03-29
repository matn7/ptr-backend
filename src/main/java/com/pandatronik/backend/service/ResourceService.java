package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.mapper.EntityMapper;
import com.pandatronik.backend.persistence.repositories.EntityRepository;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class ResourceService<DTO, Entity> {

    protected final UserService userService;
    protected final EntityRepository<Entity> entityRepository;
    protected final EntityMapper<DTO, Entity> entityMapper;

    public DTO findById(String username, Long id) {
        UserEntity userEntity = userService.findByUserName(username);
        Entity entity = entityRepository.findById(userEntity, id).orElseThrow(ResourceNotFoundException::new);
        return entityMapper.entityToDto(entity);
    }

    public DTO findByDate(String username, int year, int month, int day) {
        UserEntity userEntity = userService.findByUserName(username);
        Entity entity = entityRepository.findByDate(userEntity, day, month, year).orElseThrow(ResourceNotFoundException::new);
        return entityMapper.entityToDto(entity);
    }

    abstract public DTO save(String username, DTO dto);

//    public DTO save(String username, DTO dto) {
//        UserEntity userEntity = userService.findByUserName(username);
////        long userId = userEntity.getId();
////        dto.setUserId(userId);
//        Entity entity = entityMapper.dtoToEntity(dto);
////        entity.setUserId(userEntity);
//        return saveAndReturnDTO(entity);
//    }
//
    public DTO saveAndReturnDTO(Entity entity) {
        Entity savedEntity = entityRepository.save(entity);
        return entityMapper.entityToDto(savedEntity);
    }

    public List<DTO> findByDate(String username, int year, int month) {
        UserEntity userEntity = userService.findByUserName(username);
        return entityRepository.findByDate(userEntity, year, month)
                .stream()
                .map(entityMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public DTO update(Long id, DTO dto) {
        Entity entity = entityMapper.dtoToEntity(dto);
        return null;
    }

    public DTO delete(String username, Long id) {
        DTO resource = findById(username, id);
//        UserEntity userEntity = userService.findByUserName(username);
//        if (userEntity == null) {
//            throw new UsernameNotFoundException("Username not found");
//        }
        entityRepository.deleteById(id);
        return resource;
    }

    public List<Object[]> findCountByYearStat(String username, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        return null;
    }

    public List<Object[]> findAverageByYearStat(String username, int year) {
        UserEntity userEntity = userService.findByUserName(username);
        return null;
    }

    public List<Integer> findCountMadeByStartEnd(String username, LocalDate startDate, LocalDate endDate) {
        UserEntity userEntity = userService.findByUserName(username);
        return null;
    }


//
//    private ImportantDTO saveAndReturnDTO(ImportantEntity importantEntity) {
//        ImportantEntity savedImportant = importantRepository.save(importantEntity);
//        ImportantDTO returnDto = importantMapper.importantToImportantDTO(savedImportant);
//        return returnDto;
//    }

}
