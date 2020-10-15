package com.pandatronik.backend.persistence.mapper;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.model.DaysEntityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DaysMapper {

    DaysMapper INSTANCE = Mappers.getMapper(DaysMapper.class);

    DaysEntityDTO daysToDaysDTO(DaysEntity daysEntity);

    DaysEntity daysDtoToDays(DaysEntityDTO daysEntityDTO);
}
