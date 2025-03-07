package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.UserTotalAttendance;
import com.caci.gaia_leave.administration.model.response.UserTotalAttendanceResponse;
import com.caci.gaia_leave.administration.service.UserTotalAttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/user-total-attendance")
@RequiredArgsConstructor
public class UserTotalAttendanceController {

    private final UserTotalAttendanceService userTotalAttendanceService;

    @PostMapping("/create")
    public ResponseEntity<UserTotalAttendanceResponse> create(@Valid @RequestBody UserTotalAttendance model) {
        return userTotalAttendanceService.create(model);
    }

    @GetMapping("/read")
    public ResponseEntity<List<UserTotalAttendanceResponse>> read(){

        return userTotalAttendanceService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<UserTotalAttendanceResponse> readById(@Valid @RequestParam("id") Integer id) {
        return userTotalAttendanceService.readById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @PathVariable("id") Integer id, @RequestBody UserTotalAttendance model) {
        return userTotalAttendanceService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteById(@Valid @RequestParam("id") Integer id) {
        return userTotalAttendanceService.delete(id);
    }
}
