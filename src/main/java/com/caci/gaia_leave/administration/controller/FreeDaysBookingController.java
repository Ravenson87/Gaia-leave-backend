package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.dto.FreeDaysBookingDTO;
import com.caci.gaia_leave.administration.model.request.FreeDaysBooking;
import com.caci.gaia_leave.administration.model.request.User;
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
}
