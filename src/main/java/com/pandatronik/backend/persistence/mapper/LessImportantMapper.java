package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.model.ImportantDTO;
import com.pandatronik.backend.persistence.model.LessImportantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessImportantMapper extends EntityMapper<LessImportantDTO, LessImportantEntity>  {

    LessImportantMapper INSTANCE = Mappers.getMapper(LessImportantMapper.class);

    @Override
    @Mapping(target = "userId", ignore = true)
    LessImportantDTO entityToDto(LessImportantEntity lessImportantEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    LessImportantEntity dtoToEntity(LessImportantDTO lessImportantDTO);

}
