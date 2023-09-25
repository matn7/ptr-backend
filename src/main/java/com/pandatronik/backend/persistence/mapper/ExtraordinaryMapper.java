package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExtraordinaryMapper {

    ExtraordinaryMapper INSTANCE = Mappers.getMapper(ExtraordinaryMapper.class);

    @Mapping(target = "userId", ignore = true)
    ExtraordinaryDTO extraordinaryToExtraordinaryDTO(ExtraordinaryEntity extraordinaryEntity);

    @Mapping(target = "userId", ignore = true)
    ExtraordinaryEntity extraordinaryDtoToExtraordinary(ExtraordinaryDTO extraordinaryDTO);

}
