package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.model.DaysDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DaysRepository extends CrudRepository<DaysEntity, Long>, EntityRepository<DaysEntity, Long> {

//    Iterable<DaysEntity> findAll();

    @Query("SELECT d FROM DaysEntity d WHERE d.userId = :userId AND d.id = :id")
    Optional<DaysEntity> findById(@Param("userId") UserEntity userEntity, @Param("id") Long id);


    @Query("SELECT NEW com.pandatronik.backend.persistence.model.DaysDTO(d.id, d.body, d.rateDay, d.postedOn, d.startDate, u.id) " +
            "FROM UserEntity u " +
            "LEFT JOIN u.daysEntity d " +
            "WHERE " +
            "DAYOFMONTH(d.startDate) = :day AND MONTH(d.startDate) = :month AND YEAR(d.startDate) = :year AND " +
            "u.id = :userId")
    DaysDTO findByDateDto(@Param("userId") long userId, @Param("day") int day,
                       @Param("month") int month, @Param("year") int year);

    @Query("SELECT d FROM DaysEntity d WHERE DAYOFMONTH(d.startDate) = :day AND " +
            "MONTH(d.startDate) = :month AND YEAR(d.startDate) = :year AND d.userId = :userId")
    Optional<DaysEntity> findByDate(@Param("userId") UserEntity userEntity, @Param("day") int day,
        @Param("month") int month, @Param("year") int year);

    @Query("SELECT d FROM DaysEntity d WHERE MONTH(d.startDate) = :month AND " +
            "YEAR(d.startDate) = :year AND d.userId = :userId")
    Optional<DaysEntity> findByDateYearMonth(@Param("userId") UserEntity userEntity,
                                    @Param("month") int month, @Param("year") int year);

    @Query("SELECT d FROM DaysEntity d WHERE " +
            "MONTH(d.startDate) = :month AND YEAR(d.startDate) = :year AND d.userId = :userId")
    List<DaysEntity> findByDate(@Param("userId") UserEntity userEntity,
                                    @Param("year") int year, @Param("month") int month);

    // statistics
    @Query("SELECT rateDay FROM DaysEntity d WHERE YEAR(d.startDate) = :year AND d.userId = :userId")
    List<Integer> findByYearData(@Param("userId") UserEntity userEntity, @Param("year") int year);

    @Query("SELECT MONTH(d.startDate), AVG(rateDay) FROM DaysEntity d WHERE YEAR(d.startDate) = :year " +
        "AND d.userId = :userId GROUP BY MONTH(d.startDate)")
    List<Object[]> findAverageByYearData(@Param("userId") UserEntity userEntity, @Param("year") int year);

    @Query("SELECT rateDay, COUNT(rateDay) FROM DaysEntity d WHERE MONTH(d.startDate) = :month AND " +
            "YEAR(d.startDate) = :year AND d.userId = :userId GROUP BY d.rateDay")
    List<Object[]> findByMonthAndYearData(@Param("userId") UserEntity userEntity, @Param("month") int month,
                                             @Param("year") int year);

//    @Query("SELECT d.rateDay "
//            + " FROM CalendarEntity c"
//            + " LEFT JOIN c.days d WITH d.userId = :userId"
//            + " WHERE YEAR(c.calendarDate) = :year AND MONTH(c.calendarDate) = :month"
//            + " ORDER BY c.calendarDate")
//    Optional<List<Integer>> findByMonthAndYearDailyData(@Param("userId") UserEntity userEntity,
//        @Param("year") int year, @Param("month") int month);

}
