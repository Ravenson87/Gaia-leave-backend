package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.dto.FreeDaysBookingDTO;
import com.caci.gaia_leave.administration.model.request.FreeDaysBooking;
import com.caci.gaia_leave.administration.model.request.User;
import com.caci.gaia_leave.administration.model.response.FreeDaysBookingResponse;
import com.caci.gaia_leave.administration.service.FreeDaysBookingService;
import jakarta.validation.Valid;
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
            List<FreeDaysBookingDTO> freeDaysBookingDTO
    ) {
        return freeDaysBookingService.freeDaysRequest(freeDaysBookingDTO, userId);
    }

    @PostMapping("/read-by-status")
    public ResponseEntity<List<FreeDaysBookingResponse>> readByStatus(
            @Valid
            @RequestParam(name = "status")
            @NotNull(message = "Status can not be null")
            Integer status
    ) {
        return freeDaysBookingService.readByStatus(status);
    }

    @PostMapping("/read-by-calendar-id")
    public ResponseEntity<List<FreeDaysBookingResponse>> readByCalendarID(
            @Valid
            @RequestParam(name = "calendar_id")
            @NotNull(message = "Calendar id can not be null")
            Integer calendarId
    ) {
        return freeDaysBookingService.readByCalendarId(calendarId);
    }

    @PostMapping("/read-by-user-id")
    public ResponseEntity<List<FreeDaysBookingResponse>> readByUserId(
            @Valid
            @RequestParam(name = "user_id")
            @NotNull(message = "User Id can not be null")
            Integer userId
    ) {
        return freeDaysBookingService.readByUserId(userId);
    }
}
