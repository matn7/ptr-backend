package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.model.Important2DTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Important2Mapper {

    Important2Mapper INSTANCE = Mappers.getMapper(Important2Mapper.class);

    @Mapping(target = "userId", ignore = true)
    Important2DTO importantToImportantDTO(Important2Entity importantEntity);

    @Mapping(target = "userId", ignore = true)
    Important2Entity importantDtoToImportant(Important2DTO importantDTO);

}
