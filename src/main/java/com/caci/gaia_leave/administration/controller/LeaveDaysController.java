package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.service.JobPositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/leave-days")
@RequiredArgsConstructor
public class LeaveDaysController {

//    private final JobPositionService jobPositionService;
//    private final LeaveDaysService leaveDaysService;
//
//    @PostMapping("/create")
//    public ResponseEntity<String> create(@Valid @RequestBody LeaveDays model) {
//        return leaveDaysService.create(model);
//    }
//
//    @GetMapping("/read")
//    public ResponseEntity<List<LeaveDaysResponse>> read() {
//        return leaveDaysService.read();
//    }
//
//    @GetMapping("/read-by-id")
//    public ResponseEntity<LeaveDaysResponse> readById(@Valid @RequestParam("id") Integer id) {
//        return leaveDaysService.readById(id);
//    }
//
//    @GetMapping("/read-by-year")
//    public ResponseEntity<List<LeaveDaysResponse>> readByYear(@Valid @RequestParam("year") Integer year) {
//        return leaveDaysService.readAllByYear(year);
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<String> update(@Valid @PathVariable("id") Integer id, @RequestBody LeaveDays model) {
//        return leaveDaysService.update(id, model);
//    }
//
//    @DeleteMapping("/delete")
//    public ResponseEntity<String> deleteById(@Valid @RequestParam("id") Integer id) {
//        return leaveDaysService.delete(id);
//    }
}
