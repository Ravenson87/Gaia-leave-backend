package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.UserTotalAttendance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTotalAttendanceRepository extends CrudRepository<UserTotalAttendance, Integer> {
   Optional<UserTotalAttendance> findByUserId(Integer userId);
}
