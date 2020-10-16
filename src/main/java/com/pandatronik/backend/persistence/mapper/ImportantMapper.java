package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImportantMapper {

    ImportantMapper INSTANCE = Mappers.getMapper(ImportantMapper.class);

    ImportantDTO importantToImportantDTO(ImportantEntity importantEntity);

    ImportantEntity importantDtoToImportant(ImportantDTO importantDTO);

}
