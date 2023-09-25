package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportant3Entity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessImportant3Repository extends CrudRepository<LessImportant3Entity, Long> {

    @Query("SELECT i FROM LessImportant3Entity i WHERE i.userId = :userId AND i.id = :id")
    Optional<LessImportant3Entity> findById(@Param("userId") long userId, @Param("id") Long id);

    @Query("SELECT i FROM LessImportant3Entity i WHERE DAYOFMONTH(i.startDate) = :day AND " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userId = :userId")
    Optional<LessImportant3Entity> findByDate(@Param("userId") long userId, @Param("day") int day,
                                              @Param("month") int month, @Param("year") int year);

    @Query("SELECT i FROM LessImportant3Entity i WHERE " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userId = :userId")
    List<LessImportant3Entity> findByDate(@Param("userId") long userId,
                                              @Param("year") int year, @Param("month") int month);

    // statistics
    @Query("SELECT made, COUNT(made) FROM LessImportant3Entity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userId = :userId GROUP BY i.made")
    List<Object[]> findCountByYearStat(@Param("userId") long userId, @Param("year") int year);

    @Query("SELECT MONTH(i.startDate), AVG(made) FROM LessImportant3Entity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userId = :userId GROUP BY MONTH(i.startDate)")
    List<Object[]> findAverageByYearStat(@Param("userId") long userId, @Param("year") int year);

    @Query("SELECT made FROM LessImportant3Entity i WHERE i.startDate >= :startDate and i.startDate <= :endDate"
            + " AND i.userId = :userId")
    List<Integer> findCountMadeByStartEnd(@Param("userId") long userId, @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);
}
