package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExtraordinaryRepository extends CrudRepository<ExtraordinaryEntity, Long> {

    List<ExtraordinaryEntity> findAllByUserId(long userId);

    @Query("SELECT e FROM ExtraordinaryEntity e WHERE e.userId = :userId AND e.id = :id")
    Optional<ExtraordinaryEntity> findById(@Param("userId") long userId, @Param("id") Long id);

    @Query("SELECT e FROM ExtraordinaryEntity e WHERE DAYOFMONTH(e.startDate) = :day AND " +
            "MONTH(e.startDate) = :month AND YEAR(e.startDate) = :year AND e.userId = :userId")
    Optional<ExtraordinaryEntity> findByDate(@Param("userId") long userId,
        @Param("year") int year, @Param("month") int month, @Param("day") int day);

    @Query("SELECT e FROM ExtraordinaryEntity e WHERE " +
            "MONTH(e.startDate) = :month AND YEAR(e.startDate) = :year AND e.userId = :userId")
    List<ExtraordinaryEntity> findByPartDate(@Param("userId") long userId,
                                             @Param("year") int year, @Param("month") int month);

}
