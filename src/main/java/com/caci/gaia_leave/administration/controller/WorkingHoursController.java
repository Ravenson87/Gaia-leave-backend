package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.service.WorkingHoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/working-hours")
@RequiredArgsConstructor
public class WorkingHoursController {

    private final WorkingHoursService workingHoursService;

    @PutMapping("/update")
    public ResponseEntity<String> assignOvertimeAsFreeDays(@Valid @RequestParam("user_id") Integer userId, @RequestParam("working_hours") Integer workingHours){
       return workingHoursService.assignOvertimeAsFreeDays(userId, workingHours);
    }
}
