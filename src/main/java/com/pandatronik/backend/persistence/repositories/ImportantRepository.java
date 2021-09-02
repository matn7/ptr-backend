package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImportantRepository extends CrudRepository<ImportantEntity, Long> {

    @Query("SELECT i FROM ImportantEntity i WHERE i.userEntityId =:userEntityId AND i.id = :id")
    Optional<ImportantEntity> findById(@Param("userEntityId") long userEntityId, @Param("id") Long id);

    @Query("SELECT i FROM ImportantEntity i WHERE DAYOFMONTH(i.startDate) = :day AND " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userEntityId = :userEntityId")
    Optional<ImportantEntity> findByDate(@Param("userEntityId") long userEntityId, @Param("day") int day,
        @Param("month") int month, @Param("year") int year);

    @Query("SELECT i FROM ImportantEntity i WHERE " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userEntityId = :userEntityId")
    List<ImportantEntity> findByDate(@Param("userEntityId") long userEntityId,
                                         @Param("year") int year, @Param("month") int month);

    // statistics
    @Query("SELECT made, COUNT(made) FROM ImportantEntity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userEntityId =:userEntityId GROUP BY i.made")
    List<Object[]> findCountByYearStat(@Param("userEntityId") long userEntityId, @Param("year") int year);

    @Query("SELECT MONTH(i.startDate), AVG(made) FROM ImportantEntity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userEntityId =:userEntityId GROUP BY MONTH(i.startDate)")
    List<Object[]> findAverageByYearStat(@Param("userEntityId") long userEntityId, @Param("year") int year);

    @Query("SELECT made FROM ImportantEntity i WHERE i.startDate >= :startDate and i.startDate <= :endDate"
            + " AND i.userEntityId = :userEntityId")
    List<Integer> findCountMadeByStartEnd(@Param("userEntityId") long userEntityId, @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);


}
