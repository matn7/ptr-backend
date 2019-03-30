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

    @Query("SELECT i FROM DaysEntity i WHERE DAYOFMONTH(i.startDate) = :day AND MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userProfileId = :userProfileId")
    Optional<DaysEntity> getDaysByUidDayMonthYear(@Param("userProfileId") String userProfileId, @Param("day") int day,
                                               @Param("month") int month, @Param("year") int year);

    @Query("SELECT i FROM DaysEntity i WHERE i.userProfileId =:userProfileId AND i.id = :id")
    Optional<DaysEntity> getDaysByUidId(@Param("userProfileId") String userProfileId, @Param("id") Long id);

    @Query("SELECT COUNT(rateDay) FROM DaysEntity i WHERE YEAR(i.startDate) = :year AND i.rateDay = :rateDay AND i.userProfileId = :userProfileId")
    Integer findByUserProfileIdYearAndRateDay(@Param("userProfileId") String userProfileId, @Param("year") int year,
                                                    @Param("rateDay") int rateDay);

    @Query("SELECT COUNT(rateDay) FROM DaysEntity i WHERE MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.rateDay = :rateDay AND i.userProfileId = :userProfileId")
    Integer findCountRateDayByMonthYearRateDay(@Param("userProfileId") String userProfileId, @Param("month") int month,
                                               @Param("year") int year, @Param("rateDay") int rateDay);

    @Query("SELECT rateDay FROM DaysEntity i WHERE MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userProfileId = :userProfileId")
    List<Integer> getMonthDaysRateDay(@Param("userProfileId") String userProfileId, @Param("month") int month,
                                               @Param("year") int year);

    @Query("SELECT AVG(rateDay) FROM DaysEntity i WHERE MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userProfileId = :userProfileId ")
    Double findAvgDaysByMonthYear(@Param("userProfileId") String userProfileId, @Param("month") int month, @Param("year") int year);

}
