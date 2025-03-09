package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.Role;
import com.caci.gaia_leave.administration.model.response.RoleResponse;
import com.caci.gaia_leave.administration.service.RoleService;
import jakarta.validation.Valid;
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
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody Role model) {
        return roleService.create(model);
    }

    @GetMapping("/read")
    public ResponseEntity<List<RoleResponse>> read() {

        return roleService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<RoleResponse> readById(@Valid @RequestParam("id") Integer id) {
        return roleService.readById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @PathVariable("id") Integer id, @RequestBody Role model) {
        return roleService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteById(@Valid @RequestParam("id") Integer id) {
        return roleService.delete(id);
    }
}
