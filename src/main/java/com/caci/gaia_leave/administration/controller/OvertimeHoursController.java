package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.OvertimeHours;
import com.caci.gaia_leave.administration.model.response.OvertimeHoursResponse;
import com.caci.gaia_leave.administration.service.OvertimeHoursService;
import com.caci.gaia_leave.administration.service.WorkingHoursService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private final WorkingHoursService workingHoursService;

    @PostMapping("/create")
    public ResponseEntity<List<OvertimeHoursResponse>> create(
            @Valid
            @RequestBody
            @NotEmpty(message = "List can not be empty or null")
            List<OvertimeHours> models
    ) {
        return overtimeHoursService.create(models);
    }

    @GetMapping("/read")
    public ResponseEntity<List<OvertimeHoursResponse>> read() {
        return overtimeHoursService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<OvertimeHoursResponse> readById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return overtimeHoursService.readById(id);
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
            OvertimeHours model
    ) {
        return overtimeHoursService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return overtimeHoursService.delete(id);
    }

    @GetMapping("/sum")
    public ResponseEntity<Integer> sumOvertimeWorkingHours(
            @RequestParam("user_id")
            @NotNull(message = "User Id can not be null")
            @Min(value = 1, message = "User Id can not be less than zero")
            Integer userId,
            @RequestParam("start_date")
            @NotNull(message = "Start date can not be null")
            String startDate,
            @RequestParam("end_date")
            @NotNull(message = "End date can not be null")
            String endDate
    ) {
        return workingHoursService.sumOvertimeWorkingHours(userId, startDate, endDate);
    }

    @PutMapping("/update-overtime-compensation")
    public ResponseEntity<String> updateOvertimeCompensation(
            @Valid
            @RequestBody
            @NotEmpty(message = "List can not be empty or null")
            List<OvertimeHoursResponse> models
    ){
        return overtimeHoursService.updateOvertimeCompensation(models);
    }
}
