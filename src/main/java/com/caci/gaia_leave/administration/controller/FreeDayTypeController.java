package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.FreeDayType;
import com.caci.gaia_leave.administration.model.response.FreeDayTypeResponse;
import com.caci.gaia_leave.administration.service.FreeDayTypeService;
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
@RequestMapping("/api/v1/free-day-type")
@RequiredArgsConstructor
public class FreeDayTypeController {
    private final FreeDayTypeService freeDayTypeService;

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @Valid
            @RequestBody
            @NotNull(message = "Model can not be null")
            FreeDayType model
    ) {
        return freeDayTypeService.create(model);
    }

    @GetMapping("/read")
    public ResponseEntity<List<FreeDayTypeResponse>> read() {
        return freeDayTypeService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<FreeDayTypeResponse> readById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return freeDayTypeService.readById(id);
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
            FreeDayType model
    ) {
        return freeDayTypeService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return freeDayTypeService.delete(id);
    }
}
