package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import org.springframework.stereotype.Component;

@Component
public class DaysMapper implements EntityMapper<DaysDTO, DaysEntity> {

    @Override
    public DaysDTO entityToDto(DaysEntity daysEntity) {
        DaysDTO daysDTO = new DaysDTO();
        daysDTO.setId( daysEntity.getId() );
        daysDTO.setBody( daysEntity.getBody() );
        daysDTO.setPostedOn( daysEntity.getPostedOn() );
        daysDTO.setStartDate( daysEntity.getStartDate() );
        daysDTO.setRateDay( daysEntity.getRateDay() );
        return daysDTO;
    }

    @Override
    public DaysEntity dtoToEntity(DaysDTO daysDTO) {
        DaysEntity daysEntity = new DaysEntity();
        daysEntity.setId( daysDTO.getId() );
        daysEntity.setBody( daysDTO.getBody() );
        daysEntity.setRateDay( daysDTO.getRateDay() );
        daysEntity.setPostedOn( daysDTO.getPostedOn() );
        daysEntity.setStartDate( daysDTO.getStartDate() );
        return daysEntity;
    }
}
