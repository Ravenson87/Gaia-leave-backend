package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.response.JobPositionResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPositionResponseRepository extends CrudRepository<JobPositionResponse, Integer> {
    List<JobPositionResponse> findByIdGreaterThan(Integer id);
}
