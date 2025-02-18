package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.JobPosition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPositionRepository extends CrudRepository<JobPosition, Integer> {
}
