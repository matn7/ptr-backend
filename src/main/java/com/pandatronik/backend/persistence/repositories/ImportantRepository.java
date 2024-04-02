package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImportantRepository extends CrudRepository<ImportantEntity, Long>, EntityRepository<ImportantEntity> {

    @Override
    @Query("SELECT i FROM ImportantEntity i WHERE i.userId = :userId AND i.id = :id")
    Optional<ImportantEntity> findById(@Param("userId") UserEntity userId, @Param("id") Long id);

    // all entries created by a single user
    @Query("SELECT i FROM ImportantEntity i WHERE i.userId = :userId")
    Optional<List<ImportantEntity>> findByUserId(@Param("userId") UserEntity userEntity);

    @Override
    @Query("SELECT i FROM ImportantEntity i WHERE DAYOFMONTH(i.startDate) = :day AND " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userId = :userId")
    Optional<ImportantEntity> findByDate(@Param("userId") UserEntity userId, @Param("day") int day,
        @Param("month") int month, @Param("year") int year);

    @Override
    @Query("SELECT i FROM ImportantEntity i WHERE " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userId = :userId")
    List<ImportantEntity> findByDate(@Param("userId") UserEntity userId,
                                         @Param("year") int year, @Param("month") int month);

    // statistics
    @Query("SELECT made, COUNT(made) FROM ImportantEntity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userId = :userId GROUP BY i.made")
    List<Object[]> findCountByYearStat(@Param("userId") UserEntity userId, @Param("year") int year);

    @Query("SELECT MONTH(i.startDate), AVG(made) FROM ImportantEntity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userId = :userId GROUP BY MONTH(i.startDate)")
    List<Object[]> findAverageByYearStat(@Param("userId") UserEntity userId, @Param("year") int year);

    @Query("SELECT made FROM ImportantEntity i WHERE i.startDate >= :startDate and i.startDate <= :endDate"
            + " AND i.userId = :userId")
    List<Integer> findCountMadeByStartEnd(@Param("userId") UserEntity userId, @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);


}
