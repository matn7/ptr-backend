package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportant3Entity;
import com.pandatronik.backend.persistence.model.LessImportant3DTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessImportant3Mapper extends EntityMapper<LessImportant3DTO, LessImportant3Entity> {

    LessImportant3Mapper INSTANCE = Mappers.getMapper(LessImportant3Mapper.class);

    @Override
    @Mapping(target = "userId", ignore = true)
    LessImportant3DTO entityToDto(LessImportant3Entity lessImportantEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    LessImportant3Entity dtoToEntity(LessImportant3DTO lessImportantDTO);

}
