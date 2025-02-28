package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.JobPosition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobPositionRepository extends CrudRepository<JobPosition, Integer> {
    Optional<JobPosition> findByTitle(String title);
    Optional<JobPosition> findById(Integer id);
    boolean existsByTitle(String title);
}
