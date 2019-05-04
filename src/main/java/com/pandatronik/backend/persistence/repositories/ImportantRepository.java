package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.UserEntity;
import com.pandatronik.backend.persistence.domain.core.ImportantEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImportantRepository extends CrudRepository<ImportantEntity, Long> {

    @Query("SELECT i FROM ImportantEntity i WHERE i.userEntity =:userEntity AND i.id = :id")
    Optional<ImportantEntity> findById(@Param("userEntity") UserEntity userEntity, @Param("id") Long id);

    @Query("SELECT i FROM ImportantEntity i WHERE DAYOFMONTH(i.startDate) = :day AND " +
            "MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userEntity = :userEntity")
    Optional<ImportantEntity> findByDate(@Param("userEntity") UserEntity userEntity, @Param("day") int day,
        @Param("month") int month, @Param("year") int year);

    @Query("UPDATE ImportantEntity ie SET ie.title = :title, ie.body = :body, ie.made = :made WHERE ie.id = :id AND ie.userProfileId = :userProfileId")
    Optional<ImportantEntity> update(@Param("title") String title, @Param("body") String body, @Param("made") int made,
                                                     @Param("userProfileId") String userProfileId, @Param("id") long id);
    // statistics
    @Query("SELECT made, COUNT(made) FROM ImportantEntity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userEntity =:userEntity GROUP BY i.made")
    List<Object[]> findCountByYearStat(@Param("userEntity") UserEntity userEntity, @Param("year") int year);

    @Query("SELECT MONTH(i.startDate), AVG(made) FROM ImportantEntity i WHERE YEAR(i.startDate) = :year " +
            "AND i.userEntity =:userEntity GROUP BY MONTH(i.startDate)")
    List<Object[]> findAverageByYearStat(@Param("userEntity") UserEntity userEntity, @Param("year") int year);


}
