package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.UserTotalAttendance;
import com.caci.gaia_leave.administration.model.response.UserTotalAttendanceResponse;
import com.caci.gaia_leave.administration.service.UserTotalAttendanceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<UserTotalAttendanceResponse> create(
            @Valid
            @RequestBody
            @NotNull(message = "Model can not be null")
            UserTotalAttendance model
    ) {
        return userTotalAttendanceService.create(model);
    }

    @GetMapping("/read")
    public ResponseEntity<List<UserTotalAttendanceResponse>> read() {
        return userTotalAttendanceService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<UserTotalAttendanceResponse> readById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return userTotalAttendanceService.readById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @Valid
            @PathVariable("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id,
            @RequestBody
            @NotNull(message = "Model can not be null")
            UserTotalAttendance model
    ) {
        return userTotalAttendanceService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return userTotalAttendanceService.delete(id);
    }
}
