package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.model.Important3DTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Important3Mapper {

    Important3Mapper INSTANCE = Mappers.getMapper(Important3Mapper.class);

    Important3DTO importantToImportantDTO(Important3Entity importantEntity);

    Important3Entity importantDtoToImportant(Important3DTO importantDTO);

}
