package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImportantMapper extends EntityMapper<TaskDTO, ImportantEntity> {

    @Override
    @Mapping(target = "userId", ignore = true)
    TaskDTO entityToDto(ImportantEntity importantEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    ImportantEntity dtoToEntity(TaskDTO taskDTO);

}
