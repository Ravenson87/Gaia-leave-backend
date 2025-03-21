package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.UserUsedFreeDays;
import com.caci.gaia_leave.administration.model.response.UserUsedFreeDaysResponse;
import com.caci.gaia_leave.administration.service.UserUsedFreeDaysService;
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
@RequestMapping("/api/v1/user-used-free-days")
@RequiredArgsConstructor
public class UserUsedFreeDaysController {

    private final UserUsedFreeDaysService userUsedFreeDaysService;

    @PostMapping("/create")
    public ResponseEntity<List<UserUsedFreeDaysResponse>> create(
            @Valid
            @RequestBody
            @NotEmpty(message = "List can not be empty or null")
            List<UserUsedFreeDays> models
    ) {
        return userUsedFreeDaysService.create(models);
    }

    @GetMapping("/read")
    public ResponseEntity<List<UserUsedFreeDaysResponse>> read() {
        return userUsedFreeDaysService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<UserUsedFreeDaysResponse> read(
            @Valid
            @RequestParam
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return userUsedFreeDaysService.readById(id);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> update(
            @Valid
            @PathVariable("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id,
            @RequestBody
            @NotNull(message = "Model can not be null")
            UserUsedFreeDays model
    ) {
        return userUsedFreeDaysService.update(id, model);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @Valid
            @RequestParam
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return userUsedFreeDaysService.delete(id);
    }

    @DeleteMapping("/delete-by-ids")
    public ResponseEntity<String> deleteByIds(
            @Valid
            @RequestParam("ids")
            @NotEmpty(message = "List can not be empty or null")
            List<Integer> ids
    ) {
        return userUsedFreeDaysService.deleteByIds(ids);
    }
}
