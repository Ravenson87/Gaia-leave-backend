package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.Calendar;
import com.caci.gaia_leave.shared_tools.model.WorkingDayType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, Integer> {
    boolean existsByDate(Date date);

    boolean existsByType(WorkingDayType type);

    Optional<Calendar> findByDate(Date date);

    List<Calendar> findAllByType(WorkingDayType type);
}
