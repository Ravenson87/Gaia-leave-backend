package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.UserUsedFreeDays;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUsedFreeDaysRepository extends CrudRepository<UserUsedFreeDays, Integer> {
    boolean existsByUserIdAndCalendarId(Integer userId, Integer calendarId);

    @Query(
            name = "UserUsedFreeDays.findReligiousFreeDaysForUser",
            nativeQuery = true
    )
    UserUsedFreeDays findReligiousFreeDaysForUser(@Param("user_id")Integer userId);
}
