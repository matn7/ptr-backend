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

    List<ExtraordinaryEntity> findAllByUserEntity(UserEntity userEntity);

    @Query("SELECT i FROM ExtraordinaryEntity i WHERE i.userEntity =:userEntity AND i.id = :id")
    Optional<ExtraordinaryEntity> findById(@Param("userEntity") UserEntity userEntity, @Param("id") Long id);

    @Query("SELECT i FROM ExtraordinaryEntity i WHERE DAYOFMONTH(i.startDate) = :day AND " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userEntity = :userEntity")
    Optional<ExtraordinaryEntity> findByDate(@Param("userEntity") UserEntity userEntity,
        @Param("year") int year, @Param("month") int month, @Param("day") int day);

}
