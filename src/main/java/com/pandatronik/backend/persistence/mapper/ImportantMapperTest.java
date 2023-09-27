package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImportantMapperTest extends CustomMapper<ImportantDTO, ImportantEntity> {

    ImportantMapperTest INSTANCE = Mappers.getMapper(ImportantMapperTest.class);

    @Override
    @Mapping(target = "userId", ignore = true)
    ImportantDTO dtoToEntity(ImportantEntity importantEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    ImportantEntity entityToDto(ImportantDTO importantDTO);
}
