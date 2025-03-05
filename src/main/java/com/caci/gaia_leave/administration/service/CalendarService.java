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
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public void populateCalendar() {
        int currentYear = LocalDate.now().getYear();
        Set<Calendar> calendarSet = new LinkedHashSet<>();
        List<Date> existedDays= new ArrayList<>();

        LocalDate startDate = LocalDate.of(currentYear, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(currentYear, Month.DECEMBER, 31);

        calendarRepository.findAll().forEach(calendar -> {
            existedDays.add(calendar.getDate());
        });

        while (startDate.isBefore(endDate)) {
            Calendar calendar = new Calendar();
            boolean weekend = startDate.getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getDayOfWeek() == DayOfWeek.SUNDAY;
            Date convertedDate = AllHelpers.convertToDateViaInstant(startDate);

            calendar.setDate(convertedDate);
            DayOfWeek daysOfWeek = startDate.getDayOfWeek();
            calendar.setDays(String.valueOf(daysOfWeek).toLowerCase());
            calendar.setType(weekend ? WorkingDayType.WEEKEND : WorkingDayType.WORKING_DAY);

            startDate = startDate.plusDays(1);
            calendarSet.add(calendar);
        }

        List<Calendar> diffDays = calendarSet.stream().filter(days -> !existedDays.contains(days) ).collect(Collectors.toList());

        try {
            diffDays.forEach(System.out::println);
            calendarRepository.saveAll(diffDays);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }

    }

    public void test() {
        int currentYear = LocalDate.now().getYear();
        LocalDate startDate = LocalDate.of(currentYear, 1, 1);
        LocalDate endDate = LocalDate.of(currentYear, 12, 31);

        while (!startDate.isAfter(endDate)) {
            Date date = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            System.out.println(date);
            startDate = startDate.plusDays(1);
        }
    }

}
