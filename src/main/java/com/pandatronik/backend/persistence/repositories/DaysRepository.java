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

    @Query("SELECT i FROM DaysEntity i WHERE i.userEntityId =:userEntityId AND i.id = :id")
    Optional<DaysEntity> findById(@Param("userEntityId") long userEntityId, @Param("id") Long id);

    @Query("SELECT i FROM DaysEntity i WHERE DAYOFMONTH(i.startDate) = :day AND " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userEntityId = :userEntityId")
    Optional<DaysEntity> findByDate(@Param("userEntityId") long userEntityId, @Param("day") int day,
        @Param("month") int month, @Param("year") int year);

    @Query("SELECT i FROM DaysEntity i WHERE " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userEntityId = :userEntityId")
    List<DaysEntity> findByPartDate(@Param("userEntityId") long userEntityId,
                                    @Param("year") int year, @Param("month") int month);

    // statistics
    @Query("SELECT rateDay FROM DaysEntity i WHERE YEAR(i.startDate) = :year AND i.userEntityId = :userEntityId")
    List<Integer> findByYearData(@Param("userEntityId") long userEntityId, @Param("year") int year);

//    @Query("SELECT MONTH(i.startDate), AVG(rateDay) FROM DaysEntity i WHERE YEAR(i.startDate) = :year " +
//    "AND i.userEntityId = :userEntityId GROUP BY MONTH(i.startDate)")
//    List<Object[]> findAverageByYearData(@Param("userEntityId") long userEntityId, @Param("year") int year);

//    @Query("SELECT rateDay, COUNT(rateDay) FROM DaysEntity i WHERE MONTH(i.startDate) = :month AND " +
//            "YEAR(i.startDate) = :year AND i.userEntityId = :userEntityId GROUP BY i.rateDay")
//    List<Object[]> findByMonthAndYearData(@Param("userEntityId") long userEntityId, @Param("month") int month,
//                                             @Param("year") int year);

//    @Query("SELECT d.rateDay "
//            + " FROM CalendarEntity c"
//            + " LEFT JOIN c.days d WITH d.userEntity = :userEntity"
//            + " WHERE YEAR(c.calendarDate) = :year AND MONTH(c.calendarDate) = :month"
//            + " ORDER BY c.calendarDate")
//    Optional<List<Integer>> findByMonthAndYearDailyData(@Param("userEntity") UserEntity userEntity,
//        @Param("year") int year, @Param("month") int month);

}
