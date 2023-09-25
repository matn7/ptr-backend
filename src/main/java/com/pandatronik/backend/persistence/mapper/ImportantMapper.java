package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImportantMapper {

    ImportantMapper INSTANCE = Mappers.getMapper(ImportantMapper.class);

    @Mapping(target = "userId", ignore = true)
    ImportantDTO importantToImportantDTO(ImportantEntity importantEntity);

    @Mapping(target = "userId", ignore = true)
    ImportantEntity importantDtoToImportant(ImportantDTO importantDTO);

}
