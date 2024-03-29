package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImportantMapper extends EntityMapper<ImportantDTO, ImportantEntity> {

    ImportantMapper INSTANCE = Mappers.getMapper(ImportantMapper.class);

    @Override
    @Mapping(target = "userId", ignore = true)
    ImportantDTO entityToDto(ImportantEntity importantEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    ImportantEntity dtoToEntity(ImportantDTO importantDTO);

}
