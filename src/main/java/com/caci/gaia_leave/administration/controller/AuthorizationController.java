package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.service.AuthorizationService;
import com.caci.gaia_leave.shared_tools.model.AuthorizationTokenDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@Validated
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<AuthorizationTokenDTO> login(
            @Valid
            @RequestParam("user")
            @NotNull(message = "User can not be null")
            @NotBlank(message = "User can not be empty")
            String user,
            @RequestParam("password")
            @NotNull(message = "Password can not be null")
            @NotBlank(message = "Password can not be empty")
            String password
    ) {
        return authorizationService.login(user, password);
    }

    @PutMapping("/validate-user")
    public ResponseEntity<String> validateUser(
            @Valid
            @RequestParam("id")
            @NotNull(message = "User id can not be null")
            Integer id,
            @RequestParam("password")
            @NotEmpty(message = "Password can not be empty")
            String password,
            @RequestParam("date_of_birth")
            @NotNull(message = "Date of birth can not be null")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date dateOfBirth,
            @RequestParam("phone")
            @NotEmpty(message = "Phone can not be null")
            String phone,
            @RequestParam("date_of_holiday")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date dateOfHoliday,
            @RequestParam("holiday_description")
            String holidayDescription
    ) {
        return authorizationService.validateUser(id, password, dateOfBirth, phone, dateOfHoliday, holidayDescription);
    }

    @GetMapping("/refresh_token")
    public ResponseEntity<AuthorizationTokenDTO> refresh(
            @Valid
            @RequestParam("refresh_token")
            @NotNull(message = "Refresh Token can not be null")
            @NotBlank(message = "Refresh Token can not be empty")
            String refreshToken
    ) {
        return authorizationService.refreshToken(refreshToken);
    }
}
