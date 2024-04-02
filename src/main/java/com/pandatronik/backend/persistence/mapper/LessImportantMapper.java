package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LessImportantMapper extends EntityMapper<TaskDTO, LessImportantEntity>  {

    @Override
    @Mapping(target = "userId", ignore = true)
    TaskDTO entityToDto(LessImportantEntity lessImportantEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    LessImportantEntity dtoToEntity(TaskDTO lessImportantDTO);

}
