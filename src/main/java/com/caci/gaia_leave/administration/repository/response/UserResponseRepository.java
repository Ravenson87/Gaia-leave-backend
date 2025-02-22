package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.response.RoleResponse;
import com.caci.gaia_leave.administration.model.response.UserResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserResponseRepository extends CrudRepository<UserResponse, Integer> {

    Optional<UserResponse> findByUsernameAndStatusIsTrueOrEmailAndStatusIsTrue(String username, String email);

}