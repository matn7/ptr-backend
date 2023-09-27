package com.pandatronik.backend.persistence.mapper;

public interface EntityMapper<DTO, Entity> {

    DTO entityToDto(Entity entity);

    Entity dtoToEntity(DTO dto);

}
