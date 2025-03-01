package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.request.LeaveDays;
import com.caci.gaia_leave.administration.model.response.LeaveDaysResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveDaysResponseRepository extends CrudRepository<LeaveDaysResponse, Integer> {
    List<LeaveDaysResponse> findAllByYear(Integer year);
}
