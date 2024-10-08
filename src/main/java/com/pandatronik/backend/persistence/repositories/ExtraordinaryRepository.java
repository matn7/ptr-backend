package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExtraordinaryRepository extends CrudRepository<ExtraordinaryEntity, Long>, EntityRepository<ExtraordinaryEntity> {

    List<ExtraordinaryEntity> findAllByUserId(long userId);

    @Query("SELECT e FROM ExtraordinaryEntity e WHERE e.userId = :userId AND e.id = :id")
    Optional<ExtraordinaryEntity> findById(@Param("userId") UserEntity userId, @Param("id") Long id);

    @Query("SELECT e FROM ExtraordinaryEntity e WHERE e.userId = :userId")
    Optional<List<ExtraordinaryEntity>> findByUserId(@Param("userId") UserEntity userEntity);

    @Query("SELECT e FROM ExtraordinaryEntity e WHERE DAYOFMONTH(e.startDate) = :day AND " +
            "MONTH(e.startDate) = :month AND YEAR(e.startDate) = :year AND e.userId = :userId")
    Optional<ExtraordinaryEntity> findByDate(@Param("userId") UserEntity userId,
        @Param("day") int day, @Param("month") int month, @Param("year") int year);

    @Query("SELECT e FROM ExtraordinaryEntity e WHERE " +
            "MONTH(e.startDate) = :month AND YEAR(e.startDate) = :year AND e.userId = :userId")
    List<ExtraordinaryEntity> findByDate(@Param("userId") UserEntity userId,
                                             @Param("year") int year, @Param("month") int month);

    @Query("SELECT e FROM ExtraordinaryEntity e WHERE " +
            "MONTH(e.startDate) = :month AND YEAR(e.startDate) = :year AND e.userId = :userId")
    List<ExtraordinaryEntity> findByPartDate(@Param("userId") UserEntity userId,
                                             @Param("year") int year, @Param("month") int month);

}
