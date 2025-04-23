package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.FreeDaysBooking;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FreeDaysBookingRepository extends CrudRepository<FreeDaysBooking, Integer> {
    Optional<FreeDaysBooking> findByCalendarIdAndUserId(Integer calendarId, Integer userId);
}
