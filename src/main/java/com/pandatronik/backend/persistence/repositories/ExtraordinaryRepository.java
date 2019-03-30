package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.core.ExtraordinaryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtraordinaryRepository extends CrudRepository<ExtraordinaryEntity, Long> {

    Iterable<ExtraordinaryEntity> findAllByUserProfileId(String userProfileId);

    @Query("SELECT i FROM ExtraordinaryEntity i WHERE DAYOFMONTH(i.startDate) = :day AND MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userProfileId = :userProfileId")
    Optional<ExtraordinaryEntity> getExtraordinaryByUidDayMonthYear(@Param("userProfileId") String userProfileId, @Param("day") int day,
                                                  @Param("month") int month, @Param("year") int year);

    @Query("SELECT i FROM ExtraordinaryEntity i WHERE i.userProfileId =:userProfileId AND i.id = :id")
    Optional<ExtraordinaryEntity> getExtraordinaryByUidId(@Param("userProfileId") String userProfileId, @Param("id") Long id);

}
