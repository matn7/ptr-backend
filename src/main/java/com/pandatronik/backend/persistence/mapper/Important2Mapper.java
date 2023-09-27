package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.model.Important2DTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Important2Mapper extends EntityMapper<Important2DTO, Important2Entity> {

    Important2Mapper INSTANCE = Mappers.getMapper(Important2Mapper.class);

    @Override
    @Mapping(target = "userId", ignore = true)
    Important2DTO entityToDto(Important2Entity importantEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    Important2Entity dtoToEntity(Important2DTO importantDTO);

}
