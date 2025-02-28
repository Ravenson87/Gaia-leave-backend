package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.JobPosition;
import com.caci.gaia_leave.administration.model.response.JobPositionResponse;
import com.caci.gaia_leave.administration.repository.request.JobPositionRepository;
import com.caci.gaia_leave.administration.repository.response.JobPositionResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobPositionService {

    private final JobPositionRepository jobPositionRepository;
    private final JobPositionResponseRepository jobPositionResponseRepository;

    public ResponseEntity<String> create(JobPosition model) {

        if (jobPositionRepository.existsByTitle(model.getTitle())) {
            throw new CustomException("Job position with title `" + model.getTitle() + "` already exists.");
        }

        try {
            jobPositionRepository.save(model);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());

        }
    }

    public ResponseEntity<List<JobPositionResponse>> read() {
        List<JobPositionResponse> result = jobPositionResponseRepository.findByIdGreaterThan(1);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public ResponseEntity<JobPositionResponse> readById(Integer id) {

        if (id == 1) {
            throw new CustomException("Does not exist");
        }

        Optional<JobPositionResponse> result = jobPositionResponseRepository.findById(id);
        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public ResponseEntity<String> update(Integer id, JobPosition model) {

        if (!jobPositionRepository.existsById(id)) {
            throw new CustomException("Job position with id `" + id + "` does not exist.");
        }

        Optional<JobPosition> unique = jobPositionRepository.findByTitle(model.getTitle());
        if (unique.isPresent() && !unique.get().getId().equals(id)) {
            throw new CustomException("Job position with title `" + model.getTitle() + "` already exists.");
        }

        try {
            model.setId(id);
            jobPositionRepository.save(model);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully updated");
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<HttpStatus> delete(Integer id) {

        if (!jobPositionRepository.existsById(id)) {
            throw new CustomException("Job position with id `" + id + "` does not exist.");
        }

        try {
            jobPositionRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }
    }
}