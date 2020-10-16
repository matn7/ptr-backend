package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity2;
import com.pandatronik.backend.persistence.model.Important2DTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Important2Mapper {

    Important2Mapper INSTANCE = Mappers.getMapper(Important2Mapper.class);

    Important2DTO importantToImportantDTO(ImportantEntity2 importantEntity);

    ImportantEntity2 importantDtoToImportant(Important2DTO importantDTO);

}
