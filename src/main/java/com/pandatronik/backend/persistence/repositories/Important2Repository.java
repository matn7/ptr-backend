package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface Important2Repository extends CrudRepository<Important2Entity, Long> {

    @Query("SELECT i FROM Important2Entity i WHERE i.userEntityId =:userEntityId AND i.id = :id")
    Optional<Important2Entity> findById(@Param("userEntityId") long userEntityId, @Param("id") Long id);

    @Query("SELECT i FROM Important2Entity i WHERE DAYOFMONTH(i.startDate) = :day AND " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userEntityId = :userEntityId")
    Optional<Important2Entity> findByDate(@Param("userEntityId") long userEntityId, @Param("day") int day,
                                          @Param("month") int month, @Param("year") int year);

    @Query("SELECT i FROM Important2Entity i WHERE " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userEntityId = :userEntityId")
    List<Important2Entity> findByDate(@Param("userEntityId") long userEntityId,
                                          @Param("year") int year, @Param("month") int month);

    // statistics
    @Query("SELECT made, COUNT(made) FROM Important2Entity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userEntityId =:userEntityId GROUP BY i.made")
    List<Object[]> findCountByYearStat(@Param("userEntityId") long userEntityId, @Param("year") int year);

    @Query("SELECT MONTH(i.startDate), AVG(made) FROM Important2Entity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userEntityId =:userEntityId GROUP BY MONTH(i.startDate)")
    List<Object[]> findAverageByYearStat(@Param("userEntityId") long userEntityId, @Param("year") int year);

    @Query("SELECT made FROM Important2Entity i WHERE i.startDate >= :startDate and i.startDate <= :endDate"
            + " AND i.userEntityId = :userEntityId")
    List<Integer> findCountMadeByStartEnd(@Param("userEntityId") long userEntityId, @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

}
