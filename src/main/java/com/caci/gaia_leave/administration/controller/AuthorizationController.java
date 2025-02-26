package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.service.AuthorizationService;
import com.caci.gaia_leave.shared_tools.model.AuthorizationTokenDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<AuthorizationTokenDTO> login(@Valid @RequestParam("user") String user, @RequestParam("password") String password) {
        return authorizationService.login(user, password);
    }
}
