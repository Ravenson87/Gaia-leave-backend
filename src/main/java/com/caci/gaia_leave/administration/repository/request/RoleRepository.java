package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
}
