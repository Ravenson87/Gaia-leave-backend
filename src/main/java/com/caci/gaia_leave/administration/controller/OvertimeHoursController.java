package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.OvertimeHours;
import com.caci.gaia_leave.administration.model.response.OvertimeHoursResponse;
import com.caci.gaia_leave.administration.service.OvertimeHoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/overtime-hours")
@RequiredArgsConstructor
public class OvertimeHoursController {
    private final OvertimeHoursService overtimeHoursService;

    @PostMapping("/create")
    public ResponseEntity<OvertimeHoursResponse> create(@Valid @RequestBody OvertimeHours model) {
        return overtimeHoursService.create(model);
    }

    @GetMapping("/read")
    public ResponseEntity<List<OvertimeHoursResponse>> read() {
        return overtimeHoursService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<OvertimeHoursResponse> readById(@Valid @RequestParam("id") Integer id) {
        return overtimeHoursService.readById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @PathVariable("id") Integer id, @RequestBody OvertimeHours model) {
        return overtimeHoursService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteById(@Valid @RequestParam("id") Integer id) {
        return overtimeHoursService.delete(id);
    }
}
