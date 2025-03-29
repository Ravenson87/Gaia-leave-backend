package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.MailHistory;
import com.caci.gaia_leave.administration.repository.request.MailHistoryRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailHistoryService {
    private final MailHistoryRepository mailHistoryRepository;

    public void create(MailHistory model) {
        try{
            mailHistoryRepository.save(model);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

}
