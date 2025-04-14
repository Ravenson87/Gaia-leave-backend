package com.caci.gaia_leave.administration.controller;


import com.caci.gaia_leave.administration.model.request.Calendar;
import com.caci.gaia_leave.administration.model.response.CalendarResponse;
import com.caci.gaia_leave.administration.service.CalendarService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    @PostMapping("/create")
    public void create() {
        calendarService.populateCalendar();
    }

    @GetMapping("/read")
    public ResponseEntity<List<CalendarResponse>> read() {
        return calendarService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<CalendarResponse> readById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id

    ) {
        return calendarService.readById(id);
    }

    @GetMapping("/read-all-by-type")
    public ResponseEntity<List<CalendarResponse>> readAllByType(
            @Valid
            @RequestParam("type")
            @NotNull(message = "Type can not be null")
            @NotBlank(message = "Type can not be empty")
            String type
    ) {
        return calendarService.readAllByType(type);
    }

    @GetMapping("read-by-date")
    public ResponseEntity<CalendarResponse> readByDate(
            @Valid
            @RequestParam("date")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @NotNull(message = "Date can not be null")
            Date date
    ) {
        return calendarService.readByDate(date);
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
            Calendar model
    ) {
        return calendarService.update(id, model);
    }

    @PutMapping("/update-by-type/{id}/{type}")
    public ResponseEntity<String> updateByType(
            @Valid
            @PathVariable("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id,
            @RequestParam("type")
            @NotNull(message = "Type can not be null")
            String type,
            @RequestParam("descrpition")
            @NotNull(message = "Type can not be null")
            String description
    ) {
        return calendarService.updateByType(id, type, description);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteById(
            @Valid
            @RequestParam("id")
            @NotNull(message = "Id can not be null")
            @Min(value = 1, message = "Id can not be less than zero")
            Integer id
    ) {
        return calendarService.delete(id);
    }

    @GetMapping("/find-by-year/{year}")
    public ResponseEntity<String> findByYear(
            @Valid
            @PathVariable("year")
            @NotNull(message = "Year can not be null")
            Integer year
    ) {
        return calendarService.findByYear(year);
    }

}
