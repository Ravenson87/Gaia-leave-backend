package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.MailHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailHistoryRepository extends CrudRepository<MailHistory, Integer> {
}
