package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.Calendar;
import com.caci.gaia_leave.administration.model.response.CalendarResponse;
import com.caci.gaia_leave.administration.repository.request.CalendarRepository;
import com.caci.gaia_leave.administration.repository.response.CalendarResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import com.caci.gaia_leave.shared_tools.model.WorkingDayType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.listConverter;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final CalendarResponseRepository calendarResponseRepository;

    /**
     * Populet table Calendar in database
     */
    public void populateCalendar() {
        int currentYear = LocalDate.now().getYear();
        Set<Calendar> calendarSet = new LinkedHashSet<>();
        List<Date> existedDays = new ArrayList<>();

        // Set first and last day of the year
        LocalDate startDate = LocalDate.of(currentYear, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(currentYear, Month.DECEMBER, 31);

        //Add every calendar date to existedDays list
        calendarRepository.findAll().forEach(calendar -> {
            existedDays.add(calendar.getDate());
        });

        // Create every day in calendar with date, day name and day type
        while (!startDate.isAfter(endDate)) {
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

        // Check if day already exists in calendar
        //There was an error: parametar in !existedDays.contains() was days, instead of days.getDate().
        // That is the problem, because existedDays is list of existedDays is list of Date objects not Calendar objects
        List<Calendar> diffDays = calendarSet.stream().filter(days -> !existedDays.contains(days.getDate())).collect(Collectors.toList());

        try {
            calendarRepository.saveAll(diffDays);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }

    }


    public ResponseEntity<List<CalendarResponse>> read() {
        List<CalendarResponse> result = listConverter(calendarResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    public ResponseEntity<CalendarResponse> readById(Integer id) {
        Optional<CalendarResponse> result = calendarResponseRepository.findById(id);
        return result.map(calendar -> ResponseEntity.ok().body(calendar)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<CalendarResponse>> readAllByType(String type) {
        WorkingDayType workingDayType = WorkingDayType.valueOf(type);
        List<CalendarResponse> result = listConverter(calendarResponseRepository.findAllByType(workingDayType));
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    public ResponseEntity<CalendarResponse> readByDate(Date date) {
        Optional<CalendarResponse> result = calendarResponseRepository.findByDate(date);
        return result.map(calendar -> ResponseEntity.ok().body(calendar)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<String> update(Integer id, Calendar model) {

        if (!calendarResponseRepository.existsById(id)) {
            throw new CustomException("Day with " + id + "does not exist in calendar");
        }

        Optional<Calendar> unique = calendarRepository.findByDate(model.getDate());
        if (unique.isPresent() && !unique.get().getId().equals(id)) {
            throw new CustomException("Date`" + model.getDate() + "` already exists.");
        }

        try {
            model.setId(id);
            calendarRepository.save(model);
            return ResponseEntity.ok().body("Successfully updated");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

    public ResponseEntity<String> updateByType(Integer id, String type) {
        WorkingDayType workingDayType = getWorkingDayType(type);
        Optional<CalendarResponse> result = calendarResponseRepository.findById(id);
        CalendarResponse model = result.orElseThrow(() -> new CustomException("Day with " + id + "does not exist in calendar"));
        try {
            model.setId(id);
            model.setType(workingDayType);
            calendarResponseRepository.save(model);
            return ResponseEntity.ok().body("Successfully updated");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> delete(Integer id) {
        if (!calendarResponseRepository.existsById(id)) {
            throw new CustomException("Day with " + id + "does not exist in calendar");
        }
        try {
            calendarRepository.deleteById(id);
            return ResponseEntity.ok().body("Successfully deleted");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> findByYear(Integer year) {
        LocalDate startDate = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(year, Month.DECEMBER, 31);

        if (calendarResponseRepository.findByDateBetween(AllHelpers.convertToDateViaInstant(startDate), AllHelpers.convertToDateViaInstant(endDate)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body("Successfully found");
    }


    public static WorkingDayType getWorkingDayType(String type) {
        return switch (type) {
            case "WEEKEND" -> WorkingDayType.WEEKEND;
            case "WORKING_DAY" -> WorkingDayType.WORKING_DAY;
            case "NATIONAL_HOLIDAY" -> WorkingDayType.NATIONAL_HOLIDAY;
            default -> throw new CustomException("Invalid type");
        };
    }


}
