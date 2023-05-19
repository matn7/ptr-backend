package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.DaysEntity;
import com.pandatronik.backend.persistence.repositories.model.DaysBetween;
import com.pandatronik.backend.persistence.repositories.model.DaysMonthDateAvgRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DaysRepository extends CrudRepository<DaysEntity, Long> {

    Iterable<DaysEntity> findAll();

    @Query("SELECT i FROM DaysEntity i WHERE i.userEntity = :userEntity AND i.id = :id")
    Optional<DaysEntity> findById(@Param("userEntity") UserEntity userEntity, @Param("id") Long id);

    @Query("SELECT i FROM DaysEntity i WHERE DAYOFMONTH(i.startDate) = :day AND " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userEntity = :userEntity")
    Optional<DaysEntity> findByDate(@Param("userEntity") UserEntity userEntity,
                                    @Param("day") int day,
                                    @Param("month") int month,
                                    @Param("year") int year);

    @Query("SELECT i FROM DaysEntity i WHERE " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userEntity = :userEntity")
    List<DaysEntity> findByPartDate(@Param("userEntity") UserEntity userEntity,
                                    @Param("year") int year, @Param("month") int month);

    // SELECT rate_day, start_date FROM days WHERE user_entity_id = 1 AND YEAR(start_date) between 2017 and 2020
    @Query(value = "SELECT " +
            " new com.pandatronik.backend.persistence.repositories.model.DaysBetween(i.rateDay, i.startDate) " +
            " FROM DaysEntity i WHERE " +
            " YEAR(i.startDate) BETWEEN :yearStart AND :yearEnd AND i.userEntity = :userEntity")
    List<DaysBetween> findByYearRange(@Param("userEntity") UserEntity userEntity,
                                      @Param("yearStart") int yearStart, @Param("yearEnd") int yearEnd);

    // SELECT MONTH(start_date), AVG(rate_day) FROM days WHERE user_entity_id = 1 AND YEAR(start_date) = 2017
    // group by MONTH(start_date) order by MONTH(start_date) ASC;
    @Query(value = "SELECT " +
            " new com.pandatronik.backend.persistence.repositories.model.DaysMonthDateAvgRate(MONTH(d.startDate), AVG(d.rateDay)) " +
            " FROM DaysEntity d WHERE " +
            " d.userEntity = :userEntity AND YEAR(d.startDate) = :year GROUP BY MONTH(d.startDate) ORDER BY MONTH(d.startDate) ASC")
    List<DaysMonthDateAvgRate> findMonthAvgRateDay(@Param("userEntity") UserEntity userEntity, @Param("year") int year);
}
