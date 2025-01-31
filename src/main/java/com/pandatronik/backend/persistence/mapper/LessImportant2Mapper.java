package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportant2Entity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import org.springframework.stereotype.Component;

@Component
public class LessImportant2Mapper implements EntityMapper<TaskDTO, LessImportant2Entity> {

    @Override
    public TaskDTO entityToDto(LessImportant2Entity lessImportantEntity) {
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
    public LessImportant2Entity dtoToEntity(TaskDTO lessImportantDTO) {
        LessImportant2Entity lessImportant2Entity = new LessImportant2Entity();

        lessImportant2Entity.setId( lessImportantDTO.getId() );
        lessImportant2Entity.setTitle( lessImportantDTO.getTitle() );
        lessImportant2Entity.setBody( lessImportantDTO.getBody() );
        lessImportant2Entity.setMade( lessImportantDTO.getMade() );
        lessImportant2Entity.setPostedOn( lessImportantDTO.getPostedOn() );
        lessImportant2Entity.setStartDate( lessImportantDTO.getStartDate() );

        return lessImportant2Entity;
    }

}
