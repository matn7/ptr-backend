package com.pandatronik.backend.persistence.mapper;

import com.pandatronik.backend.persistence.domain.core.LessImportant3Entity;
import com.pandatronik.backend.persistence.model.TaskDTO;
import org.springframework.stereotype.Component;

@Component
public class LessImportant3Mapper implements EntityMapper<TaskDTO, LessImportant3Entity> {

    @Override
    public TaskDTO entityToDto(LessImportant3Entity lessImportantEntity) {
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
    public LessImportant3Entity dtoToEntity(TaskDTO lessImportantDTO) {
        LessImportant3Entity lessImportant3Entity = new LessImportant3Entity();

        lessImportant3Entity.setId( lessImportantDTO.getId() );
        lessImportant3Entity.setTitle( lessImportantDTO.getTitle() );
        lessImportant3Entity.setBody( lessImportantDTO.getBody() );
        lessImportant3Entity.setMade( lessImportantDTO.getMade() );
        lessImportant3Entity.setPostedOn( lessImportantDTO.getPostedOn() );
        lessImportant3Entity.setStartDate( lessImportantDTO.getStartDate() );

        return lessImportant3Entity;
    }
}
