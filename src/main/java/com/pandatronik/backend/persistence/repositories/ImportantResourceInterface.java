package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// extends CrudRepository<T, U>
public interface ImportantResourceInterface<T, U>{
    Optional<T> findById(UserEntity userId, U id);
}
