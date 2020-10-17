package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportant2Entity;
import com.pandatronik.backend.persistence.model.LessImportant2DTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessImportant2Mapper {

    LessImportant2Mapper INSTANCE = Mappers.getMapper(LessImportant2Mapper.class);

    LessImportant2DTO lessImportantToLessImportantDTO(LessImportant2Entity lessImportantEntity);

    LessImportant2Entity lessImportantDtoToLessImportant(LessImportant2DTO lessImportantDTO);

}
