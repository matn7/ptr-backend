package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import org.springframework.stereotype.Component;

@Component
public class ImportantMapper implements EntityMapper<TaskDTO, ImportantEntity> {

    @Override
    public TaskDTO entityToDto(ImportantEntity importantEntity) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId( importantEntity.getId() );
        taskDTO.setBody( importantEntity.getBody() );
        taskDTO.setPostedOn( importantEntity.getPostedOn() );
        taskDTO.setStartDate( importantEntity.getStartDate() );
        taskDTO.setTitle( importantEntity.getTitle() );
        taskDTO.setMade( importantEntity.getMade() );

        return taskDTO;
    }

    @Override
    public ImportantEntity dtoToEntity(TaskDTO taskDTO) {
        ImportantEntity importantEntity = new ImportantEntity();

        importantEntity.setId( taskDTO.getId() );
        importantEntity.setTitle( taskDTO.getTitle() );
        importantEntity.setBody( taskDTO.getBody() );
        importantEntity.setMade( taskDTO.getMade() );
        importantEntity.setPostedOn( taskDTO.getPostedOn() );
        importantEntity.setStartDate( taskDTO.getStartDate() );

        return importantEntity;
    }

}
