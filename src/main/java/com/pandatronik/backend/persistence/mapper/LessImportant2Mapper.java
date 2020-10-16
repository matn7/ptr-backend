package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportantEntity2;
import com.pandatronik.backend.persistence.model.LessImportant2DTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessImportant2Mapper {

    LessImportant2Mapper INSTANCE = Mappers.getMapper(LessImportant2Mapper.class);

    LessImportant2DTO lessImportantToLessImportantDTO(LessImportantEntity2 lessImportantEntity);

    LessImportantEntity2 lessImportantDtoToLessImportant(LessImportant2DTO lessImportantDTO);

}
