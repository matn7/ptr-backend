package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExtraordinaryMapper {

    ExtraordinaryMapper INSTANCE = Mappers.getMapper(ExtraordinaryMapper.class);

    ExtraordinaryDTO extraordinaryToExtraordinaryDTO(ExtraordinaryEntity extraordinaryEntity);

    ExtraordinaryEntity extraordinaryDtoToExtraordinary(ExtraordinaryDTO extraordinaryDTO);

}
