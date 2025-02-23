package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.RefreshToken;
import com.caci.gaia_leave.administration.model.request.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {

}
