package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.FreeDayType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreeDayTypeRepository extends CrudRepository<FreeDayType, Integer> {
    boolean existsByType(String type);
    Optional<FreeDayType> findByType(String type);
}
