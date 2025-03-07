package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.response.CalendarResponse;
import com.caci.gaia_leave.shared_tools.model.WorkingDayType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarResponseRepository extends CrudRepository<CalendarResponse, Integer> {
    Optional<CalendarResponse> findByDate(Date date);
    List<CalendarResponse> findAllByType(WorkingDayType type);
    List<CalendarResponse> findByDateBetween(Date start, Date end);
}
