package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.response.UserResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserResponseRepository extends CrudRepository<UserResponse, Integer> {

    Optional<UserResponse> findByUsernameAndStatusIsTrueOrEmailAndStatusIsTrue(String username, String email);

    Optional<UserResponse> findByRefreshTokenAndStatusIsTrue(String refreshToken);

    Optional<UserResponse> findByUsername(String username);

    Optional<UserResponse> findByEmail(String email);

    Optional<UserResponse> findByHash(String hash);

    List<UserResponse> findByIdGreaterThan(Integer id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}