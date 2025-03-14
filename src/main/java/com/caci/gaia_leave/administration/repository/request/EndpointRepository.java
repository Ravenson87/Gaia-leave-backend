package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.Endpoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndpointRepository extends CrudRepository<Endpoint, String> {

}
