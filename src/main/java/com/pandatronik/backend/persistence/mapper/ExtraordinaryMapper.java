package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExtraordinaryMapper extends EntityMapper<ExtraordinaryDTO, ExtraordinaryEntity> {

    @Override
    @Mapping(target = "userId", ignore = true)
    ExtraordinaryDTO entityToDto(ExtraordinaryEntity extraordinaryEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    ExtraordinaryEntity dtoToEntity(ExtraordinaryDTO extraordinaryDTO);

}
