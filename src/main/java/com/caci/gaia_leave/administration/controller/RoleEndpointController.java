package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.RoleEndpoint;
import com.caci.gaia_leave.administration.model.response.RoleEndpointResponse;
import com.caci.gaia_leave.administration.service.RoleEndpointService;
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
@RequestMapping("/api/v1/role-endpoint")
@RequiredArgsConstructor
public class RoleEndpointController {

    private final RoleEndpointService roleEndpointService;

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @Valid
            @RequestBody
            @NotEmpty(message = "List can not be null or empty")
            List<RoleEndpoint> models) {
        return roleEndpointService.create(models);
    }

    @GetMapping("/read")
    public ResponseEntity<List<RoleEndpointResponse>> read() {

        return roleEndpointService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<RoleEndpointResponse> readById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return roleEndpointService.readById(id);
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
            RoleEndpoint model
    ) {
        return roleEndpointService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return roleEndpointService.delete(id);
    }

    @DeleteMapping("/delete-by-ids")
    public ResponseEntity<String> deleteByIds(
            @Valid
            @RequestParam("ids")
            @NotEmpty(message = "List can not be empty or null")
            List<Integer> ids) {
        return roleEndpointService.deleteByIds(ids);
    }
}
