package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DaysMapper extends EntityMapper<DaysDTO, DaysEntity> {

    @Override
    @Mapping(target = "userId", ignore = true)
    DaysDTO entityToDto(DaysEntity daysEntity);

    @Override
    @Mapping(target = "userId", ignore = true)
    DaysEntity dtoToEntity(DaysDTO daysDTO);
}
