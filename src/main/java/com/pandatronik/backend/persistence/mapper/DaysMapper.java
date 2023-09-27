package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DaysMapper {

    @Mapping(target = "userId", ignore = true)
    DaysDTO daysToDaysDTO(DaysEntity daysEntity);

    @Mapping(target = "userId", ignore = true)
    DaysEntity daysDtoToDays(DaysDTO daysDTO);
}
