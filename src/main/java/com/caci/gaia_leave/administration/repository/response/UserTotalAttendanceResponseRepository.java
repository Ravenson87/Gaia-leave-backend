package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.response.UserTotalAttendanceResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTotalAttendanceResponseRepository extends CrudRepository<UserTotalAttendanceResponse, Integer> {
}
