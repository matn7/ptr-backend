package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.LessImportantEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessImportantRepository extends CrudRepository<LessImportantEntity, Long> {

    @Query("SELECT i FROM LessImportantEntity i WHERE i.userEntity =:userEntity AND i.id = :id")
    Optional<LessImportantEntity> findById(@Param("userEntity") UserEntity userEntity, @Param("id") Long id);

    @Query("SELECT i FROM LessImportantEntity i WHERE DAYOFMONTH(i.startDate) = :day AND " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userEntity = :userEntity")
    Optional<LessImportantEntity> findByDate(@Param("userEntity") UserEntity userEntity, @Param("day") int day,
        @Param("month") int month, @Param("year") int year);

    // statistics
    @Query("SELECT made, COUNT(made) FROM LessImportantEntity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userEntity = :userEntity GROUP BY i.made")
    List<Object[]> findCountByYearStat(@Param("userEntity") UserEntity userEntity, @Param("year") int year);

    @Query("SELECT MONTH(i.startDate), AVG(made) FROM LessImportantEntity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userEntity = :userEntity GROUP BY MONTH(i.startDate)")
    List<Object[]> findAverageByYearStat(@Param("userEntity") UserEntity userEntity, @Param("year") int year);

    @Query("SELECT made FROM LessImportantEntity i WHERE i.startDate >= :startDate and i.startDate <= :endDate"
            + " AND i.userEntity = :userEntity")
    List<Integer> findCountMadeByStartEnd(@Param("userEntity") UserEntity userEntity, @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);
}
