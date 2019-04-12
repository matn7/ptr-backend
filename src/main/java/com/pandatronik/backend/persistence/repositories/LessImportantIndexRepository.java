package com.pandatronik.backend.persistence.repositories;

import com.pandatronik.backend.persistence.domain.core.CalendarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessImportantIndexRepository extends CrudRepository<CalendarEntity, Long> {

	@Query("SELECT r.id, r.title, i.id, i.made, i.title, "
			+ "	i2.id, i2.made, i2.title, i3.id, i3.made, i3.title, d.id, d.rateDay"
			+ " FROM CalendarEntity c"
			+ " LEFT JOIN c.extraordinary r WITH r.userProfileId = :name"
			+ " LEFT JOIN c.lessImportant i WITH i.userProfileId = :name"
			+ " LEFT JOIN c.lessImportant2 i2 WITH i2.userProfileId = :name"
			+ " LEFT JOIN c.lessImportant3 i3 WITH i3.userProfileId = :name"
			+ " LEFT JOIN c.days d WITH d.userProfileId = :name"
			+ " WHERE YEAR(c.calendarDate) = :year AND MONTH(c.calendarDate) = :month"
			+ " ORDER BY c.calendarDate")
	Optional<List<Object[]>> findIndexData(@Param("name") String name,
		@Param("year") int year, @Param("month") int month);

}
