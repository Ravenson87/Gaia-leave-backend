package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.JobPosition;
import com.caci.gaia_leave.administration.model.response.JobPositionResponse;
import com.caci.gaia_leave.administration.service.JobPositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/job-position")
@RequiredArgsConstructor
public class JobPositionController {

    private final JobPositionService jobPositionService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody JobPosition model) {
        return jobPositionService.create(model);
    }

    @GetMapping("/read")
    public ResponseEntity<List<JobPositionResponse>> read() {
        return jobPositionService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<JobPositionResponse> readById(@Valid @RequestParam("id") Integer id) {
        return jobPositionService.readById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @PathVariable("id") Integer id, @RequestBody JobPosition model) {
        return jobPositionService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteById(@Valid @RequestParam("id") Integer id) {
        return jobPositionService.delete(id);
    }
}
