package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.dto.UserUpdateDTO;
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
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/read-by-hash")
    public ResponseEntity<Boolean> readByHash(
            @Valid
            @RequestParam("hash")
            @NotNull(message = "Hash can not be null")
            String hash
    ) {
        return userService.checkLinkExpired(hash);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(
            @Valid
            @RequestBody
            @NotNull(message = "Model can not be null")
            UserUpdateDTO model
    ) {
        return userService.update(model);
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

    @PutMapping("/upload-image")
    public ResponseEntity<String> uploadImage(
            @Valid
            @RequestParam("file")
            @NotNull
            MultipartFile file,
            @RequestParam("user_id")
            @NotNull
            @Min(value = 1, message = "Id can not be less than zero")
            Integer userId
    ) {
        return userService.uploadImage(file, userId);
    }

}
