package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.response.OvertimeHoursResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OvertimeHoursResponseRepository extends CrudRepository<OvertimeHoursResponse, Integer> {
    boolean existsByUserIdAndCalendarId(Integer userId, Integer calendarId);

    Optional<OvertimeHoursResponse> findByUserIdAndCalendarId(Integer userId, Integer calendarId);
}
