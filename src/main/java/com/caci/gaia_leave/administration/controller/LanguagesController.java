package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.Languages;
import com.caci.gaia_leave.administration.model.response.LanguagesResponse;
import com.caci.gaia_leave.administration.service.LanguagesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/language")
@RequiredArgsConstructor
public class LanguagesController {

    private final LanguagesService languagesService;

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @Valid
            @RequestBody
            @NotNull(message = "Model can not be null")
            Languages model
    ) {
        return languagesService.create(model);
    }

    @GetMapping("/read")
    public ResponseEntity<List<LanguagesResponse>> read() {
        return languagesService.read();
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
            Languages model
    ) {
        return languagesService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return languagesService.delete(id);
    }
}
