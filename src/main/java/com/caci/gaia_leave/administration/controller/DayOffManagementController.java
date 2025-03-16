package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.UserUsedFreeDays;
import com.caci.gaia_leave.administration.service.DayOffManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/day-off-management")
@RequiredArgsConstructor
public class DayOffManagementController {

    private final DayOffManagementService dayOffManagementService;

    @PutMapping("/save")
    public ResponseEntity<String> subtractDaysFromFreeDays(@Valid @RequestBody List<UserUsedFreeDays> usersFreeDays) {
       return dayOffManagementService.subtractDaysFromFreeDays(usersFreeDays, true);
    }

    @PutMapping("/delete")
    public ResponseEntity<String> addDaysFromFreeDays(@Valid @RequestBody List<UserUsedFreeDays> usersFreeDays) {
        return dayOffManagementService.subtractDaysFromFreeDays(usersFreeDays, false);
    }
}
