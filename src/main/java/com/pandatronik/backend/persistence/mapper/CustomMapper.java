package com.pandatronik.backend.persistence.mapper;

public interface CustomMapper<D, E> {

    D dtoToEntity(E importantEntity);

    E entityToDto(D importantDTO);

}
