package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.Calendar;
import com.caci.gaia_leave.administration.repository.request.CalendarRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import com.caci.gaia_leave.shared_tools.model.WorkingDayType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public void populateCalendar() {
        Date today = new Date();
        Set<Calendar> calendarSet = new LinkedHashSet<>();

        LocalDate date = LocalDate.of(today.getYear(), Month.JANUARY, 1);

        while (date.getYear() == today.getYear()) {
            Calendar calendar = new Calendar();
            boolean weekend = date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
            Date convertedDate = AllHelpers.convertToDateViaInstant(date);

            System.out.println("Converted date: " + convertedDate);
            System.out.println("Weekend: " + (weekend ? WorkingDayType.WEEKEND : WorkingDayType.WORKING_DAY));
            calendar.setDate(convertedDate);
            DayOfWeek daysOfWeek = date.getDayOfWeek();
            calendar.setDays(String.valueOf(daysOfWeek).toLowerCase());
            calendar.setType(weekend ? WorkingDayType.WEEKEND : WorkingDayType.WORKING_DAY);

            date = date.plusDays(1);

            System.out.println("Converted date: " + calendarRepository.existsByDate(convertedDate));
            if (calendarRepository.existsByDate(convertedDate)) {
                System.out.println("Calendar already exists");
                continue;
            }

            calendarSet.add(calendar);
        }

        try {
            calendarRepository.saveAll(calendarSet);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }

    }

}
