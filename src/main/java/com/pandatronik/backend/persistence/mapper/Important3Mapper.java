package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.model.Important3DTO;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Important3Mapper extends EntityMapper<Important3DTO, Important3Entity> {

    Important3Mapper INSTANCE = Mappers.getMapper(Important3Mapper.class);

    @Override
    @Mapping(target = "userId", ignore = true)
    Important3DTO entityToDto(Important3Entity importantEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    Important3Entity dtoToEntity(Important3DTO importantDTO);

}
