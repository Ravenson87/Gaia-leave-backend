package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.dto.FreeDaysBookingDTO;
import com.caci.gaia_leave.administration.model.dto.FreeDaysBookingUpdateDTO;
import com.caci.gaia_leave.administration.model.response.FreeDaysBookingResponse;
import com.caci.gaia_leave.administration.service.FreeDaysBookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/free-days-booking")
@RequiredArgsConstructor
public class FreeDaysBookingController {
    private final FreeDaysBookingService freeDaysBookingService;

    @PostMapping("/request/{user_id}")
    public ResponseEntity<String> freeDaysRequest(
            @Valid
            @PathVariable("user_id")
            @NotNull(message = "User ID can not be null")
            Integer userId,
            @RequestBody
            @NotNull(message = "Model can not be null")
            List<FreeDaysBookingDTO> model
    ) {
        return freeDaysBookingService.freeDaysRequest(model, userId);
    }

    @GetMapping("/read")
    public ResponseEntity<List<FreeDaysBookingResponse>> read(){
       return freeDaysBookingService.read();

    }

    @GetMapping("/read-by-status")
    public ResponseEntity<List<FreeDaysBookingResponse>> readByStatus(
            @Valid
            @RequestParam(name = "status")
            @NotNull(message = "Status can not be null")
            Integer status
    ) {
        return freeDaysBookingService.readByStatus(status);
    }

    @GetMapping("/read-by-calendar-id")
    public ResponseEntity<List<FreeDaysBookingResponse>> readByCalendarID(
            @Valid
            @RequestParam(name = "calendar_id")
            @NotNull(message = "Calendar id can not be null")
            Integer calendarId
    ) {
        return freeDaysBookingService.readByCalendarId(calendarId);
    }

    @GetMapping("/read-by-user-id")
    public ResponseEntity<List<FreeDaysBookingResponse>> readByUserId(
            @Valid
            @RequestParam(name = "user_id")
            @NotNull(message = "User Id can not be null")
            Integer userId
    ) {
        return freeDaysBookingService.readByUserId(userId);
    }

    @GetMapping("/read-by-date-range-and-status")
    public ResponseEntity<List<FreeDaysBookingResponse>> readByDateRangeAndStatus(
            @Valid
            @RequestParam(name = "start_date")
            @NotEmpty(message = "start_date can not be null or empty")
            String startDate,
            @RequestParam(name = "end_date")
            @NotEmpty(message = "end_date can not be null or empty")
            String endDate,
            @RequestParam(name = "status")
            @NotNull(message = "Status can not be null")
            Integer status
    ) {
        return freeDaysBookingService.readByDateRangeAndStatus(startDate, endDate, status);
    }

    @PostMapping("/acceptance")
    public ResponseEntity<String> freeDaysAcceptance(
            @Valid
            @RequestBody
            @NotNull(message = "Model can not be null")
            List<FreeDaysBookingUpdateDTO> freeDaysBookingUpdateDTO
    ) {
        return freeDaysBookingService.freeDaysAcceptance(freeDaysBookingUpdateDTO);
    }
}
