package com.caci.gaia_leave.administration.controller;


import com.caci.gaia_leave.administration.model.request.Calendar;


import com.caci.gaia_leave.administration.model.response.CalendarResponse;
import com.caci.gaia_leave.administration.service.CalendarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/read")
    public ResponseEntity<List<CalendarResponse>> read() {
        return calendarService.read();
    }

    @GetMapping("/read-by-id")
    public ResponseEntity<CalendarResponse> readById(@Valid @RequestParam("id") Integer id) {
        return calendarService.readById(id);
    }

    @GetMapping("/read-all-by-type")
    public ResponseEntity<List<CalendarResponse>> readAllByType(@Valid @RequestParam("type") String type) {
        return calendarService.readAllByType(type);
    }

    @GetMapping("read-by-date")
    public ResponseEntity<CalendarResponse> readByDate(@Valid @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return calendarService.readByDate(date);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@Valid @PathVariable("id") Integer id, @RequestBody Calendar model) {
        return calendarService.update(id, model);
    }

    @PutMapping("/update-by-type/{id}/{type}")
    public ResponseEntity<String> updateByType(@Valid @PathVariable("id") Integer id, @Valid @PathVariable("type") String type) {
        return calendarService.updateByType(id, type);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteById(@Valid @RequestParam("id") Integer id) {
        return calendarService.delete(id);
    }

    @GetMapping("/find-by-year/{year}")
    public ResponseEntity<String> findByYear(@Valid @PathVariable("year") Integer year) {
        return calendarService.findByYear(year);
    }


}
