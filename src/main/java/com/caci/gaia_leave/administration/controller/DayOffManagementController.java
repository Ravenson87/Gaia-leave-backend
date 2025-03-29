package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.UserUsedFreeDays;
import com.caci.gaia_leave.administration.service.DayOffManagementService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/day-off-management")
@RequiredArgsConstructor
public class DayOffManagementController {

    private final DayOffManagementService dayOffManagementService;

    @PutMapping("/save")
    public ResponseEntity<String> subtractDaysFromFreeDays(
            @Valid
            @RequestBody
            @NotEmpty(message = "List can not be null or empty")
            List<UserUsedFreeDays> usersFreeDays
    ) {
        return dayOffManagementService.subtractDaysFromFreeDays(usersFreeDays, true);
    }

    @PutMapping("/delete")
    public ResponseEntity<String> addDaysFromFreeDays(
            @Valid
            @RequestBody
            @NotEmpty(message = "List can not be null or empty")
            List<UserUsedFreeDays> usersFreeDays
    ) {
        return dayOffManagementService.subtractDaysFromFreeDays(usersFreeDays, false);
    }
}
