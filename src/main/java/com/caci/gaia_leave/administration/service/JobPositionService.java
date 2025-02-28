package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.JobPosition;
import com.caci.gaia_leave.administration.repository.request.JobPositionRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
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

    public ResponseEntity<String> create(JobPosition model){
        Optional<JobPosition> check = jobPositionRepository.findByTitle(model.getTitle());

        if (check.isPresent()) {
            throw new CustomException("JobPosition with title `" + model.getTitle() + "` already exists.");
        }

        try{
            jobPositionRepository.save(model);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
        }catch(CustomException e){
            throw new CustomException(e.getMessage());

        }
    }

    public ResponseEntity<List<JobPosition>> read(){
        List<JobPosition> result = AllHelpers.listConverter(jobPositionRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public ResponseEntity<JobPosition> readById(Integer id) {
        Optional<JobPosition> result = jobPositionRepository.findById(id);
        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public ResponseEntity<String> update(Integer id, JobPosition model){
        if(!jobPositionRepository.existsById(id)){
            throw new CustomException("JobPosition with id `" + id + "` does not exist.");
        }
        Optional<JobPosition> unique = jobPositionRepository.findById(model.getId());
        if (unique.isPresent() && unique.get().getId().equals(id)) {
            throw new CustomException("JobPosition with title `" + model.getTitle() + "` already exists.");
        }
        try{
            model.setId(id);
            jobPositionRepository.save(model);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully updated");
        }catch(CustomException e){
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<HttpStatus> delete(Integer id){
        if(!jobPositionRepository.existsById(id)){
            throw new CustomException("JobPosition with id `" + id + "` does not exist.");
        }
        try{
            jobPositionRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(CustomException e){
            throw new CustomException(e.getMessage());
        }
    }
}
