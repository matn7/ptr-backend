package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Important3Mapper extends EntityMapper<TaskDTO, Important3Entity> {

    @Override
    @Mapping(target = "userId", ignore = true)
    TaskDTO entityToDto(Important3Entity importantEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    Important3Entity dtoToEntity(TaskDTO taskDTO);

}
