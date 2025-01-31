package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.model.ExtraordinaryDTO;
import org.springframework.stereotype.Component;

@Component
public class ExtraordinaryMapper implements EntityMapper<ExtraordinaryDTO, ExtraordinaryEntity> {

    @Override
    public ExtraordinaryDTO entityToDto(ExtraordinaryEntity extraordinaryEntity) {
        ExtraordinaryDTO extraordinaryDTO = new ExtraordinaryDTO();
        extraordinaryDTO.setId( extraordinaryEntity.getId() );
        extraordinaryDTO.setBody( extraordinaryEntity.getBody() );
        extraordinaryDTO.setPostedOn( extraordinaryEntity.getPostedOn() );
        extraordinaryDTO.setStartDate( extraordinaryEntity.getStartDate() );
        extraordinaryDTO.setTitle( extraordinaryEntity.getTitle() );
        return extraordinaryDTO;
    }

    @Override
    public ExtraordinaryEntity dtoToEntity(ExtraordinaryDTO extraordinaryDTO) {
        ExtraordinaryEntity extraordinaryEntity = new ExtraordinaryEntity();

        extraordinaryEntity.setId( extraordinaryDTO.getId() );
        extraordinaryEntity.setTitle( extraordinaryDTO.getTitle() );
        extraordinaryEntity.setBody( extraordinaryDTO.getBody() );
        extraordinaryEntity.setPostedOn( extraordinaryDTO.getPostedOn() );
        extraordinaryEntity.setStartDate( extraordinaryDTO.getStartDate() );

        return extraordinaryEntity;
    }

}
