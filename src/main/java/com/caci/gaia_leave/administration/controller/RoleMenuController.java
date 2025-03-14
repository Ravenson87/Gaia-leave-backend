package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.RoleMenu;
import com.caci.gaia_leave.administration.model.request.UserTotalAttendance;
import com.caci.gaia_leave.administration.model.response.RoleMenuResponse;
import com.caci.gaia_leave.administration.model.response.UserTotalAttendanceResponse;
import com.caci.gaia_leave.administration.service.RoleMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/role-menu")
@RequiredArgsConstructor
public class RoleMenuController {
    private final RoleMenuService roleMenuService;

    @PostMapping("/create")
    public ResponseEntity<RoleMenuResponse> create(@Valid @RequestBody RoleMenu model) {
        return roleMenuService.create(model);
    }

    @GetMapping("/read")
    public ResponseEntity<List<RoleMenuResponse>> read() {

        return roleMenuService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<RoleMenuResponse> readById(@Valid @RequestParam("id") Integer id) {
        return roleMenuService.readById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @Valid @PathVariable("id") Integer id, @RequestBody RoleMenu model) {
        return roleMenuService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteById(@Valid @RequestParam("id") Integer id) {
        return roleMenuService.delete(id);
    }
}
