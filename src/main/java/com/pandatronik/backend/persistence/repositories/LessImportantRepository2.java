package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.core.LessImportantEntity2;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessImportantRepository2 extends CrudRepository<LessImportantEntity2, Long> {

    @Query("SELECT i FROM LessImportantEntity2 i WHERE i.userProfileId =:userProfileId AND i.id = :id")
    Optional<LessImportantEntity2> getLessImportantByUidId(@Param("userProfileId") String userProfileId, @Param("id") Long id);

    @Query("SELECT i FROM LessImportantEntity2 i WHERE DAYOFMONTH(i.startDate) = :day AND MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userProfileId = :userProfileId")
    Optional<LessImportantEntity2> getLessImportantByUidDayMonthYeat(@Param("userProfileId") String userProfileId, @Param("day") int day,
                                                             @Param("month") int month, @Param("year") int year);

    @Query("UPDATE LessImportantEntity2 ie SET ie.title = :title, ie.body = :body, ie.made = :made WHERE ie.id = :id AND ie.userProfileId = :userProfileId")
    Optional<LessImportantEntity2> updateLessImportantByUidId(@Param("title") String title, @Param("body") String body, @Param("made") int made,
                                                      @Param("userProfileId") String userProfileId, @Param("id") long id);

    @Query("SELECT COUNT(made) FROM LessImportantEntity2 i WHERE YEAR(i.startDate) = :year AND i.userProfileId = :userProfileId AND i.made = :made")
    Integer countTaskMadeInYear(@Param("userProfileId") String userProfileId, @Param("year") int year, @Param("made") int made);

    @Query("SELECT AVG(made) FROM LessImportantEntity2 i WHERE MONTH(i.startDate) = :month AND YEAR(i.startDate) = :year AND i.userProfileId = :userProfileId ")
    Double findAvgMadeByMonthYear(@Param("userProfileId") String userProfileId, @Param("month") int month, @Param("year") int year);

}
