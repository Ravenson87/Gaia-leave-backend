package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.Menu;
import com.caci.gaia_leave.administration.model.response.MenuResponse;
import com.caci.gaia_leave.administration.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody Menu model) {
        return menuService.create(model);
    }

    @GetMapping("/read")
    public ResponseEntity<List<MenuResponse>> read() {
        return menuService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<MenuResponse> readById(@Valid @RequestParam("id") Integer id) {
        return menuService.readById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @PathVariable("id") Integer id, @RequestBody Menu model) {
        return menuService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteById(@Valid @RequestParam("id") Integer id) {
        return menuService.delete(id);
    }

}
