package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportant2Entity;
import com.pandatronik.backend.persistence.model.LessImportant2DTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessImportant2Mapper extends EntityMapper<LessImportant2DTO, LessImportant2Entity> {

    LessImportant2Mapper INSTANCE = Mappers.getMapper(LessImportant2Mapper.class);

    @Override
    @Mapping(target = "userId", ignore = true)
    LessImportant2DTO entityToDto(LessImportant2Entity lessImportantEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    LessImportant2Entity dtoToEntity(LessImportant2DTO lessImportantDTO);

}
