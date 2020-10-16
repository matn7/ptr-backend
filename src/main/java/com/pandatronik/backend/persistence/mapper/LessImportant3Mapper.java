package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportantEntity3;
import com.pandatronik.backend.persistence.model.LessImportant3DTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LessImportant3Mapper {

    LessImportant3Mapper INSTANCE = Mappers.getMapper(LessImportant3Mapper.class);

    LessImportant3DTO lessImportantToLessImportantDTO(LessImportantEntity3 lessImportantEntity);

    LessImportantEntity3 lessImportantDtoToLessImportant(LessImportant3DTO lessImportantDTO);

}
