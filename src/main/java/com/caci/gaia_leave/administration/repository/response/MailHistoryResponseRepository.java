package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.response.MailHistoryResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailHistoryResponseRepository extends CrudRepository<MailHistoryResponse, Integer> {
}
