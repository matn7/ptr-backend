package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity3;
import com.pandatronik.backend.persistence.model.Important3DTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Important3Mapper {

    Important3Mapper INSTANCE = Mappers.getMapper(Important3Mapper.class);

    Important3DTO importantToImportantDTO(ImportantEntity3 importantEntity);

    ImportantEntity3 importantDtoToImportant(Important3DTO importantDTO);

}
