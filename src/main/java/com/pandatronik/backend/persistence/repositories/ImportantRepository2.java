package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.core.ImportantEntity2;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImportantRepository2 extends CrudRepository<ImportantEntity2, Long> {

    @Query("SELECT i FROM ImportantEntity2 i WHERE i.userProfileId =:userProfileId AND i.id = :id")
    Optional<ImportantEntity2> findById(@Param("userProfileId") String userProfileId, @Param("id") Long id);

    @Query("SELECT i FROM ImportantEntity2 i WHERE DAYOFMONTH(i.startDate) = :day AND MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userProfileId = :userProfileId")
    Optional<ImportantEntity2> findByDate(@Param("userProfileId") String userProfileId, @Param("day") int day,
                                                            @Param("month") int month, @Param("year") int year);

    @Query("UPDATE ImportantEntity2 ie SET ie.title = :title, ie.body = :body, ie.made = :made WHERE ie.id = :id AND ie.userProfileId = :userProfileId")
    Optional<ImportantEntity2> update(@Param("title") String title, @Param("body") String body, @Param("made") int made,
                                                     @Param("userProfileId") String userProfileId, @Param("id") long id);

    // statistics
    @Query("SELECT made, COUNT(made) FROM ImportantEntity2 i WHERE YEAR(i.startDate) = :year " +
            "AND i.userProfileId = :userProfileId GROUP BY i.made")
    List<Object[]> findCountByYearStat(@Param("userProfileId") String userProfileId, @Param("year") int year);

    @Query("SELECT MONTH(i.startDate), AVG(made) FROM ImportantEntity2 i WHERE YEAR(i.startDate) = :year " +
            "AND i.userProfileId = :userProfileId GROUP BY MONTH(i.startDate)")
    List<Object[]> findAverageByYearStat(@Param("userProfileId") String userProfileId, @Param("year") int year);

}
