package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.model.LessImportantDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessImportantMapper {

    LessImportantMapper INSTANCE = Mappers.getMapper(LessImportantMapper.class);

    LessImportantDTO lessImportantToLessImportantDTO(LessImportantEntity lessImportantEntity);

    LessImportantEntity lessImportantDtoToLessImportant(LessImportantDTO lessImportantDTO);

}
