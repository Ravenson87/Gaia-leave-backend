package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.response.RoleResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleResponseRepository extends CrudRepository<RoleResponse, Integer> {

    Optional<RoleResponse> findByName(String name);

    List<RoleResponse> findByIdGreaterThan(Integer id);

    boolean existsByName(String name);
}
