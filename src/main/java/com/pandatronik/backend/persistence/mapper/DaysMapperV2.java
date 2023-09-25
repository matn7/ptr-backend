package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import org.mapstruct.factory.Mappers;

public class DaysMapperV2 implements DaysMapper {

    DaysMapper INSTANCE = Mappers.getMapper(DaysMapper.class);

    @Override
    public DaysDTO daysToDaysDTO(DaysEntity daysEntity) {
        DaysDTO daysDTO = new DaysDTO();
        daysDTO.setId(daysEntity.getId());
        daysDTO.setBody(daysEntity.getBody());
        daysDTO.setRateDay(daysEntity.getRateDay());
        daysDTO.setPostedOn(daysEntity.getPostedOn());
        daysDTO.setStartDate(daysEntity.getStartDate());
        daysDTO.setUserId(daysEntity.getId());
        return daysDTO;
    }

    @Override
    public DaysEntity daysDtoToDays(DaysDTO daysDTO) {
        DaysEntity daysEntity = new DaysEntity();
        daysEntity.setId(daysDTO.getId());
        daysEntity.setBody(daysDTO.getBody());
        daysEntity.setRateDay(daysDTO.getRateDay());
        daysEntity.setPostedOn(daysDTO.getPostedOn());
        daysEntity.setStartDate(daysDTO.getStartDate());
        daysEntity.setUserId(null);
        return daysEntity;
    }

}
