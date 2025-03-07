package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.OvertimeHours;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OvertimeHoursRepository extends CrudRepository<OvertimeHours, Integer> {
    boolean existsByUserIdAndCalendarId(Integer userId, Integer calendarId);
}
