package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.User;
import com.caci.gaia_leave.administration.model.response.UserResponse;
import com.caci.gaia_leave.administration.service.UserService;
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
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(
            @Valid
            @RequestBody
            @NotNull(message = "Model can not be null")
            User model
    ) {
        return userService.create(model);
    }

    @GetMapping("/read")
    public ResponseEntity<List<UserResponse>> read() {

        return userService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<UserResponse> readById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return userService.readById(id);
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
            User model
    ) {
        return userService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return userService.delete(id);
    }

    @PutMapping("/update-password/{id}")
    public ResponseEntity<String> updatePassword(
            @Valid
            @PathVariable("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id,
            @RequestParam("old_password")
            @NotNull(message = "Password can not be null")
            @NotEmpty(message = "Password can not be empty")
            String oldPassword,
            @RequestParam("new_password")
            @NotNull(message = "Password can not be null")
            @NotEmpty(message = "Password can not be empty")
            String newPassword
    ) {
        return userService.updatePassword(id, oldPassword, newPassword);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<String> updateStatus(
            @Valid @PathVariable("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id,
            @RequestParam("status")
            Boolean status
    ) {
        return userService.updateStatus(id, status);
    }

}
