package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.model.LessImportantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessImportantMapper {

    LessImportantMapper INSTANCE = Mappers.getMapper(LessImportantMapper.class);

    @Mapping(target = "userId", ignore = true)
    LessImportantDTO lessImportantToLessImportantDTO(LessImportantEntity lessImportantEntity);

    @Mapping(target = "userId", ignore = true)
    LessImportantEntity lessImportantDtoToLessImportant(LessImportantDTO lessImportantDTO);

}
