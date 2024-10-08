package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.Important2Entity;
import com.pandatronik.backend.persistence.domain.core.LessImportant2Entity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import org.h2.engine.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessImportantRepository extends CrudRepository<LessImportantEntity, Long>, EntityRepository<LessImportantEntity>  {

    @Query("SELECT i FROM LessImportantEntity i WHERE i.userId = :userId AND i.id = :id")
    Optional<LessImportantEntity> findById(@Param("userId") UserEntity userId, @Param("id") Long id);

    @Query("SELECT i FROM LessImportantEntity i WHERE i.userId = :userId")
    Optional<List<LessImportantEntity>> findByUserId(@Param("userId") UserEntity userEntity);

    @Query("SELECT i FROM LessImportantEntity i WHERE DAYOFMONTH(i.startDate) = :day AND " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userId = :userId")
    Optional<LessImportantEntity> findByDate(@Param("userId") UserEntity userId, @Param("day") int day,
        @Param("month") int month, @Param("year") int year);

    // SELECT * FROM pandatronik_dev.less_important WHERE MONTH(start_date) = 9 AND YEAR(start_date) = 2023 AND user_entity_id = 1;
    @Query("SELECT i FROM LessImportantEntity i WHERE " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userId = :userId")
    List<LessImportantEntity> findByDate(@Param("userId") UserEntity userId,
                                             @Param("year") int year, @Param("month") int month);

    // statistics
    @Query("SELECT made, COUNT(made) FROM LessImportantEntity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userId = :userId GROUP BY i.made")
    List<Object[]> findCountByYearStat(@Param("userId") UserEntity userId, @Param("year") int year);

    @Query("SELECT MONTH(i.startDate), AVG(made) FROM LessImportantEntity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userId = :userId GROUP BY MONTH(i.startDate)")
    List<Object[]> findAverageByYearStat(@Param("userId") UserEntity userId, @Param("year") int year);

    @Query("SELECT made FROM LessImportantEntity i WHERE i.startDate >= :startDate and i.startDate <= :endDate"
            + " AND i.userId = :userId")
    List<Integer> findCountMadeByStartEnd(@Param("userId") UserEntity userId, @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);
}
