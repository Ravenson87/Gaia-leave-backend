package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.Role;
import com.caci.gaia_leave.administration.model.response.RoleResponse;
import com.caci.gaia_leave.administration.service.RoleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<RoleResponse> createRole(
            @Valid
            @RequestBody
            @NotNull(message = "Model can not be null")
            Role model
    ) {
        return roleService.create(model);
    }

    @GetMapping("/read")
    public ResponseEntity<List<RoleResponse>> read() {

        return roleService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<RoleResponse> readById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return roleService.readById(id);
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
            Role model
    ) {
        return roleService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return roleService.delete(id);
    }
}
