package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExtraordinaryRepository extends CrudRepository<ExtraordinaryEntity, Long> {

    List<ExtraordinaryEntity> findAllById(long userEntity);

    @Query("SELECT i FROM ExtraordinaryEntity i WHERE i.userEntityId =:userEntityId AND i.id = :id")
    Optional<ExtraordinaryEntity> findById(@Param("userEntityId") long userEntityId, @Param("id") Long id);

    @Query("SELECT i FROM ExtraordinaryEntity i WHERE DAYOFMONTH(i.startDate) = :day AND " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userEntityId = :userEntityId")
    Optional<ExtraordinaryEntity> findByDate(@Param("userEntityId") long userEntityId,
        @Param("year") int year, @Param("month") int month, @Param("day") int day);

    @Query("SELECT i FROM ExtraordinaryEntity i WHERE " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userEntityId = :userEntityId")
    List<ExtraordinaryEntity> findByPartDate(@Param("userEntityId") long userEntityId,
                                             @Param("year") int year, @Param("month") int month);

}
