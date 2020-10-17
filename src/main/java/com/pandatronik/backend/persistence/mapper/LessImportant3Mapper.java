package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportant3Entity;
import com.pandatronik.backend.persistence.model.LessImportant3DTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessImportant3Mapper {

    LessImportant3Mapper INSTANCE = Mappers.getMapper(LessImportant3Mapper.class);

    LessImportant3DTO lessImportantToLessImportantDTO(LessImportant3Entity lessImportantEntity);

    LessImportant3Entity lessImportantDtoToLessImportant(LessImportant3DTO lessImportantDTO);

}
