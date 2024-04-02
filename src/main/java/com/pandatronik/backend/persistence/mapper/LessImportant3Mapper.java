package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportant3Entity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LessImportant3Mapper extends EntityMapper<TaskDTO, LessImportant3Entity> {

    @Override
    @Mapping(target = "userId", ignore = true)
    TaskDTO entityToDto(LessImportant3Entity lessImportantEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    LessImportant3Entity dtoToEntity(TaskDTO lessImportantDTO);

}
