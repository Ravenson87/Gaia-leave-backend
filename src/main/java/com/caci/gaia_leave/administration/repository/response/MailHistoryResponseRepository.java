package com.caci.gaia_leave.administration.repository.response;

import com.caci.gaia_leave.administration.model.request.MailHistory;
import com.caci.gaia_leave.administration.model.response.MailHistoryResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MailHistoryResponseRepository extends CrudRepository<MailHistoryResponse, Integer> {
    List<MailHistory> findByAddresses(String message);
    List<MailHistory> findByMessage(String message);
}
