package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.request.FreeDaysBooking;
import com.caci.gaia_leave.administration.model.response.FreeDaysBookingResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FreeDaysBookingResponseRepository extends CrudRepository<FreeDaysBookingResponse, Integer> {
    List<FreeDaysBookingResponse> readByStatus(Integer status);
    List<FreeDaysBookingResponse> readByCalendarId(Integer calendarId);
    List<FreeDaysBookingResponse> readByUserId(Integer userId);
    @Query(
            name = "FreeDaysType.freeDaysTypeRequestByDateRangeAndStatus",
            nativeQuery = true
    )
    List<FreeDaysBookingResponse> freeDaysTypeRequestByDateRangeAndStatus(@Param("start_date") Date start_date, @Param("end_date") Date end_date, @Param("status") String status);
}
