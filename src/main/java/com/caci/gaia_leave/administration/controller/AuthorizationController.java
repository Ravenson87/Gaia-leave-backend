package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.dto.UserValidationDTO;
import com.caci.gaia_leave.administration.service.AuthorizationService;
import com.caci.gaia_leave.shared_tools.model.AuthorizationTokenDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody
            @NotNull(message = "Model can not be null")
            UserValidationDTO model
    ) {
        return authorizationService.validateUser(model);
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
