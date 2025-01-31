package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import org.springframework.stereotype.Component;

@Component
public class Important2Mapper implements EntityMapper<TaskDTO, Important2Entity> {

    @Override
    public TaskDTO entityToDto(Important2Entity importantEntity) {
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
    public Important2Entity dtoToEntity(TaskDTO taskDTO) {
        Important2Entity important2Entity = new Important2Entity();

        important2Entity.setId( taskDTO.getId() );
        important2Entity.setTitle( taskDTO.getTitle() );
        important2Entity.setBody( taskDTO.getBody() );
        important2Entity.setMade( taskDTO.getMade() );
        important2Entity.setPostedOn( taskDTO.getPostedOn() );
        important2Entity.setStartDate( taskDTO.getStartDate() );

        return important2Entity;
    }

}
