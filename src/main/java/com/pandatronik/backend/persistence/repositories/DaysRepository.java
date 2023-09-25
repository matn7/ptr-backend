package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DaysRepository extends CrudRepository<DaysEntity, Long> {

    Iterable<DaysEntity> findAll();

    @Query("SELECT d FROM DaysEntity d WHERE d.userId = :userId AND d.id = :id")
    Optional<DaysEntity> findById(@Param("userId") long userId, @Param("id") Long id);

    @Query("SELECT d FROM DaysEntity d WHERE DAYOFMONTH(d.startDate) = :day AND " +
            "MONTH(d.startDate) = :month AND YEAR(d.startDate) = :year AND d.userId = :userId")
    Optional<DaysEntity> findByDate(@Param("userId") long userId, @Param("day") int day,
        @Param("month") int month, @Param("year") int year);

    @Query("SELECT d FROM DaysEntity d WHERE MONTH(d.startDate) = :month AND " +
            "YEAR(d.startDate) = :year AND d.userId = :userId")
    Optional<DaysEntity> findByDateYearMonth(@Param("userId") long userId,
                                    @Param("month") int month, @Param("year") int year);

    @Query("SELECT d FROM DaysEntity d WHERE " +
            "MONTH(d.startDate) = :month AND YEAR(d.startDate) = :year AND d.userId = :userId")
    List<DaysEntity> findByPartDate(@Param("userId") long userId,
                                    @Param("year") int year, @Param("month") int month);

    // statistics
    @Query("SELECT rateDay FROM DaysEntity d WHERE YEAR(d.startDate) = :year AND d.userId = :userId")
    List<Integer> findByYearData(@Param("userId") long userId, @Param("year") int year);

    @Query("SELECT MONTH(d.startDate), AVG(rateDay) FROM DaysEntity d WHERE YEAR(d.startDate) = :year " +
        "AND d.userId = :userId GROUP BY MONTH(d.startDate)")
    List<Object[]> findAverageByYearData(@Param("userId") long userId, @Param("year") int year);

    @Query("SELECT rateDay, COUNT(rateDay) FROM DaysEntity d WHERE MONTH(d.startDate) = :month AND " +
            "YEAR(d.startDate) = :year AND d.userId = :userId GROUP BY d.rateDay")
    List<Object[]> findByMonthAndYearData(@Param("userId") long userId, @Param("month") int month,
                                             @Param("year") int year);

    @Query("SELECT d.rateDay "
            + " FROM CalendarEntity c"
            + " LEFT JOIN c.days d WITH d.userId = :userId"
            + " WHERE YEAR(c.calendarDate) = :year AND MONTH(c.calendarDate) = :month"
            + " ORDER BY c.calendarDate")
    Optional<List<Integer>> findByMonthAndYearDailyData(@Param("userId") long userId,
        @Param("year") int year, @Param("month") int month);

}
