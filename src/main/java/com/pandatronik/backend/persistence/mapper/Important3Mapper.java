package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import org.springframework.stereotype.Component;

@Component
public class Important3Mapper implements EntityMapper<TaskDTO, Important3Entity> {

    @Override
    public TaskDTO entityToDto(Important3Entity importantEntity) {
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
    public Important3Entity dtoToEntity(TaskDTO taskDTO) {
        Important3Entity important3Entity = new Important3Entity();

        important3Entity.setId( taskDTO.getId() );
        important3Entity.setTitle( taskDTO.getTitle() );
        important3Entity.setBody( taskDTO.getBody() );
        important3Entity.setMade( taskDTO.getMade() );
        important3Entity.setPostedOn( taskDTO.getPostedOn() );
        important3Entity.setStartDate( taskDTO.getStartDate() );

        return important3Entity;
    }

}
