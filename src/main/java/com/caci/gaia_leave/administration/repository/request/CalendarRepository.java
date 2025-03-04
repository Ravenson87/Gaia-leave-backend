package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.Calendar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, Integer> {
    boolean existsByDate(Date date);
}
