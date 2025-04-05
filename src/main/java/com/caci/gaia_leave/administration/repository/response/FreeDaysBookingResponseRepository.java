package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.request.FreeDaysBooking;
import com.caci.gaia_leave.administration.model.response.FreeDaysBookingResponse;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FreeDaysBookingResponseRepository extends CrudRepository<FreeDaysBookingResponse, Integer> {
    List<FreeDaysBookingResponse> readByStatus(Integer status);
    List<FreeDaysBookingResponse> readByCalendarId(Integer calendarId);
    List<FreeDaysBookingResponse> readByUserId(Integer userId);
}
