package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.Calendar;
import com.caci.gaia_leave.administration.model.request.FreeDaysBooking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FreeDaysBookingRepository extends CrudRepository<FreeDaysBooking, Integer> {

}
