package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.domain.core.Important3Entity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface Important3Repository extends CrudRepository<Important3Entity, Long>, EntityRepository<Important3Entity> {

    @Query("SELECT i FROM Important3Entity i WHERE i.userId =:userId AND i.id = :id")
    Optional<Important3Entity> findById(@Param("userId") UserEntity userId, @Param("id") Long id);

    // all entries created by a single user
    @Query("SELECT i FROM Important3Entity i WHERE i.userId = :userId")
    Optional<List<Important3Entity>> findByUserId(@Param("userId") UserEntity userEntity);

    @Query("SELECT i FROM Important3Entity i WHERE DAYOFMONTH(i.startDate) = :day AND " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userId = :userId")
    Optional<Important3Entity> findByDate(@Param("userId") UserEntity userId, @Param("day") int day,
                                          @Param("month") int month, @Param("year") int year);

    @Query("SELECT i FROM Important3Entity i WHERE " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userId = :userId")
    List<Important3Entity> findByDate(@Param("userId") UserEntity userId,
                                          @Param("year") int year, @Param("month") int month);

    // statistics
    @Query("SELECT made, COUNT(made) FROM Important3Entity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userId = :userId GROUP BY i.made")
    List<Object[]> findCountByYearStat(@Param("userId") UserEntity userId, @Param("year") int year);

    @Query("SELECT MONTH(i.startDate), AVG(made) FROM Important3Entity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userId = :userId GROUP BY MONTH(i.startDate)")
    List<Object[]> findAverageByYearStat(@Param("userId") UserEntity userId, @Param("year") int year);

    @Query("SELECT made FROM Important3Entity i WHERE i.startDate >= :startDate and i.startDate <= :endDate"
            + " AND i.userId = :userId")
    List<Integer> findCountMadeByStartEnd(@Param("userId") UserEntity userId, @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

}
