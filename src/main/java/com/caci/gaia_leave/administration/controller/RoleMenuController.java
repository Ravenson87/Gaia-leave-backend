package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.RoleMenu;
import com.caci.gaia_leave.administration.model.response.RoleMenuResponse;
import com.caci.gaia_leave.administration.service.RoleMenuService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<String> create(
            @Valid
            @RequestBody
            @NotEmpty(message = "List can not be empty or null")
            List<RoleMenu> models
    ) {
        return roleMenuService.create(models);
    }

    @GetMapping("/read")
    public ResponseEntity<List<RoleMenuResponse>> read() {

        return roleMenuService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<RoleMenuResponse> readById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return roleMenuService.readById(id);
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
            RoleMenu model
    ) {
        return roleMenuService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return roleMenuService.delete(id);
    }

    @DeleteMapping("/delete-by-ids")
    public ResponseEntity<String> deleteByIds(
            @Valid
            @RequestParam("ids")
            @NotEmpty(message = "List can not be empty or null")
            List<Integer> ids
    ) {
        return roleMenuService.deleteByIds(ids);
    }
}
