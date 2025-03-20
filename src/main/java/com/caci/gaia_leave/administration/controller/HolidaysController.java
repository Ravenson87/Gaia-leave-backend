package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.model.request.Calendar;
import com.caci.gaia_leave.administration.model.request.FreeDayType;
import com.caci.gaia_leave.administration.service.HolidayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/holidays")
@RequiredArgsConstructor
public class HolidaysController {

    private final HolidayService holidayService;

    @PutMapping("/update")
    public ResponseEntity<String>  updateHolidays(@Valid @RequestBody List<Calendar> holidays){
       return holidayService.updateHolidays(holidays);

    }
}
