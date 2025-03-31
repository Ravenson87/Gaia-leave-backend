package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.MailHistory;
import com.caci.gaia_leave.administration.repository.request.MailHistoryRepository;
import com.caci.gaia_leave.administration.repository.response.MailHistoryResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.listConverter;

@Service
@RequiredArgsConstructor
public class MailHistoryService {
    private final MailHistoryRepository mailHistoryRepository;
    private final MailHistoryResponseRepository mailHistoryResponseRepository;

    public void create(MailHistory model) {
        try{
            mailHistoryRepository.save(model);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<List<MailHistory>> read() {
        List<MailHistory> result = listConverter(mailHistoryRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    public ResponseEntity<MailHistory> readById(Integer id) {
        Optional<MailHistory> result = mailHistoryRepository.findById(id);
        return result.map(mail -> ResponseEntity.ok().body(mail)).orElse(ResponseEntity.noContent().build());
    }

    public ResponseEntity<List<MailHistory>> readByAddresses(String addresses) {
        List<MailHistory> result = listConverter(mailHistoryResponseRepository.findByAddresses(addresses));
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    public ResponseEntity<List<MailHistory>> readByMessage(String message) {
        List<MailHistory> result = listConverter(mailHistoryResponseRepository.findByMessage(message));
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }
}
