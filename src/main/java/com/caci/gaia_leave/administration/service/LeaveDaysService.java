package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.LeaveDays;
import com.caci.gaia_leave.administration.model.response.LeaveDaysResponse;
import com.caci.gaia_leave.administration.repository.request.LeaveDaysRepository;
import com.caci.gaia_leave.administration.repository.response.LeaveDaysResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveDaysService {
    private final LeaveDaysRepository leaveDaysRepository;
    private final LeaveDaysResponseRepository leaveDaysResponseRepository;

    public ResponseEntity<String> create(LeaveDays model) {

        if (leaveDaysRepository.existsByDate(model.getDate())) {
            throw new CustomException("Date already exists");
        }

        try {
            leaveDaysRepository.save(model);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<List<LeaveDaysResponse>> read() {
        List<LeaveDaysResponse> res = AllHelpers.listConverter(leaveDaysResponseRepository.findAll());
        return res.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.OK).body(res);
    }

    public ResponseEntity<LeaveDaysResponse> readById(Integer id) {

        Optional<LeaveDaysResponse> res = leaveDaysResponseRepository.findById(id);

        return res
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public ResponseEntity<List<LeaveDaysResponse>> readAllByYear(Integer year) {

        List<LeaveDaysResponse> res = leaveDaysResponseRepository.findAllByYear(year);

        return res.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.OK).body(res);
    }

    public ResponseEntity<String> update(Integer id, LeaveDays model) {

        if (!leaveDaysRepository.existsById(id)) {
            throw new CustomException("Leave days id not found");
        }

        Optional<LeaveDays> check = leaveDaysRepository.findByDate(model.getDate());
        if (check.isPresent() && !check.get().getId().equals(id)) {
            throw new CustomException("Leave days with date `" + model.getDate() + "` not found");
        }

        try {
            model.setId(id);
            leaveDaysRepository.save(model);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully updated");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> delete(Integer id) {

        if (!leaveDaysRepository.existsById(id)) {
            throw new CustomException("Leave days id not found");
        }

        try {
            leaveDaysRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    public List<LeaveDays> populate(LeaveDays model, String pattern) {
        if (leaveDaysRepository.findByYear(model.getYear())) {
            throw new CustomException("Year already exists");
        }
        List<String> weekends = AllHelpers.getWeekends(LocalDate.now().getYear(), pattern) ;

        return null;
    }


}
