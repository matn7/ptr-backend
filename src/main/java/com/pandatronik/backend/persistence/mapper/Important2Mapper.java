package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Important2Mapper extends EntityMapper<TaskDTO, Important2Entity> {

    @Override
    @Mapping(target = "userId", ignore = true)
    TaskDTO entityToDto(Important2Entity importantEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    Important2Entity dtoToEntity(TaskDTO taskDTO);

}
