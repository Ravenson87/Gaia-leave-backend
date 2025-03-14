package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.RoleEndpoint;
import com.caci.gaia_leave.administration.model.response.RoleEndpointResponse;
import com.caci.gaia_leave.administration.service.RoleEndpointService;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> create(@Valid @RequestBody List<RoleEndpoint> models) {
        return roleEndpointService.create(models);
    }

    @GetMapping("/read")
    public ResponseEntity<List<RoleEndpointResponse>> read() {

        return roleEndpointService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<RoleEndpointResponse> readById(@Valid @RequestParam("id") Integer id) {
        return roleEndpointService.readById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(
            @Valid @PathVariable("id") Integer id, @RequestBody RoleEndpoint model) {
        return roleEndpointService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteById(@Valid @RequestParam("id") Integer id) {
        return roleEndpointService.delete(id);
    }

    @DeleteMapping("/delete-by-ids")
    public ResponseEntity<String> deleteByIds(@Valid @RequestParam("ids") List<Integer> ids) {
        return roleEndpointService.deleteByIds(ids);
    }
}
