package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportant2Entity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LessImportant2Mapper extends EntityMapper<TaskDTO, LessImportant2Entity> {

    @Override
    @Mapping(target = "userId", ignore = true)
    TaskDTO entityToDto(LessImportant2Entity lessImportantEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    LessImportant2Entity dtoToEntity(TaskDTO lessImportantDTO);

}
