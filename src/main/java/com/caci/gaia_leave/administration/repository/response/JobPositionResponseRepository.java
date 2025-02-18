package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.response.JobPositionResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPositionResponseRepository extends CrudRepository<JobPositionResponse, Integer> {
}
