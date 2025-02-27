package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.response.RoleResponse;
import com.caci.gaia_leave.administration.model.response.UserResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserResponseRepository extends CrudRepository<UserResponse, Integer> {

    Optional<UserResponse> findByUsername(String username);
    Optional<UserResponse> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<UserResponse> findByUsernameAndStatusIsTrueOrEmailAndStatusIsTrue(String username, String email);
    Optional<UserResponse> findByRefreshTokenAndStatusIsTrue(String refreshToken);

}