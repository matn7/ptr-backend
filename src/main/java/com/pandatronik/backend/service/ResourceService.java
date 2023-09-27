package com.pandatronik.backend.service;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.mapper.CustomMapper;
import com.pandatronik.backend.persistence.model.DtoInterface;
import com.pandatronik.backend.persistence.repositories.ImportantResourceInterface;
import com.pandatronik.backend.service.user.account.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

//@RequiredArgsConstructor
public abstract class ResourceService<R extends ImportantResourceInterface, M extends CustomMapper, D extends DtoInterface> {

//    protected final UserService userService;
//    protected final R importantRepository;
//    protected final M mapper;

//    public D findById(String username, Long id, ResourceFunction resourceFunction) {
//        UserEntity userEntity = userService.findByUserName(username);
//        resourceFunction.apply();
//        return importantRepository.findById(userEntity, id)
//                .map(mapper::dtoToEntity)
//                .orElseThrow(ResourceNotFoundException::new);
//    }

//    public D findByDate(String username, int year, int month, int day) {
//        UserEntity userEntity = userService.findByUserName(username);
//        return importantRepository.findByDate(userEntity, day, month, year)
//                .map(importantMapper::importantToImportantDTO)
//                .orElseThrow(ResourceNotFoundException::new);
//    }
//
//    public List<D> findByDate(String username, int year, int month) {
//        UserEntity userEntity = userService.findByUserName(username);
//        return importantRepository.findByDate(userEntity, year, month)
//                .stream()
//                .map(importantMapper::importantToImportantDTO)
//                .collect(Collectors.toList());
//    }
//
//    public ImportantDTO save(String username, ImportantDTO importantDTO) {
//        UserEntity userEntity = userService.findByUserName(username);
//        long userId = userEntity.getId();
//        importantDTO.setUserId(userId);
//        ImportantEntity important = importantMapper.importantDtoToImportant(importantDTO);
//        important.setUserId(userEntity);
//        return saveAndReturnDTO(important);
//    }
//
//    public ImportantDTO update(Long id, ImportantDTO importantDTO) {
//        ImportantEntity important = importantMapper.importantDtoToImportant(importantDTO);
//        important.setId(id);
//        return saveAndReturnDTO(important);
//    }
//
//    public void delete(String username, Long id) {
//        importantRepository.deleteById(id);
//    }
//
//    public List<Object[]> findCountByYearStat(String username, int year) {
//        UserEntity userEntity = userService.findByUserName(username);
//        return importantRepository.findCountByYearStat(userEntity, year);
//    }
//
//    public List<Object[]> findAverageByYearStat(String username, int year) {
//        UserEntity userEntity = userService.findByUserName(username);
//        return importantRepository.findAverageByYearStat(userEntity, year);
//    }
//
//    public List<Integer> findCountMadeByStartEnd(String username, LocalDate startDate, LocalDate endDate) {
//        UserEntity userEntity = userService.findByUserName(username);
//        return importantRepository.findCountMadeByStartEnd(userEntity, startDate, endDate);
//    }
//
//    private ImportantDTO saveAndReturnDTO(ImportantEntity importantEntity) {
//        ImportantEntity savedImportant = importantRepository.save(importantEntity);
//        ImportantDTO returnDto = importantMapper.importantToImportantDTO(savedImportant);
//        return returnDto;
//    }

}
