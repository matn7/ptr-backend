package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import org.springframework.stereotype.Component;

@Component
public class LessImportantMapper implements EntityMapper<TaskDTO, LessImportantEntity>  {

    @Override
    public TaskDTO entityToDto(LessImportantEntity lessImportantEntity) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId( lessImportantEntity.getId() );
        taskDTO.setBody( lessImportantEntity.getBody() );
        taskDTO.setPostedOn( lessImportantEntity.getPostedOn() );
        taskDTO.setStartDate( lessImportantEntity.getStartDate() );
        taskDTO.setTitle( lessImportantEntity.getTitle() );
        taskDTO.setMade( lessImportantEntity.getMade() );

        return taskDTO;
    }

    @Override
    public LessImportantEntity dtoToEntity(TaskDTO lessImportantDTO) {
        LessImportantEntity lessImportantEntity = new LessImportantEntity();

        lessImportantEntity.setId( lessImportantDTO.getId() );
        lessImportantEntity.setTitle( lessImportantDTO.getTitle() );
        lessImportantEntity.setBody( lessImportantDTO.getBody() );
        lessImportantEntity.setMade( lessImportantDTO.getMade() );
        lessImportantEntity.setPostedOn( lessImportantDTO.getPostedOn() );
        lessImportantEntity.setStartDate( lessImportantDTO.getStartDate() );

        return lessImportantEntity;
    }

}
