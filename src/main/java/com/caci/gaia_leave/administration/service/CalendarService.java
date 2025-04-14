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
     * Populate table Calendar in database
     */
    public void populateCalendar() {
        int currentYear = LocalDate.now().getYear();
        Set<Calendar> calendarSet = new LinkedHashSet<>();
        List<Date> existedDays = new ArrayList<>();

        // Set first and last day of the year
        LocalDate startDate = LocalDate.of(currentYear, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(currentYear, Month.DECEMBER, 31);

        //Add every calendar date to existedDays list
        calendarRepository.findAll().forEach(calendar -> existedDays.add(calendar.getDate()));

        // Create every day in calendar with date, day name and day type
        while (!startDate.isAfter(endDate)) {
            Calendar calendar = new Calendar();
            boolean weekend = startDate.getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getDayOfWeek() == DayOfWeek.SUNDAY;
            Date convertedDate = AllHelpers.convertToDateViaInstant(startDate);

            calendar.setDate(convertedDate);
            DayOfWeek daysOfWeek = startDate.getDayOfWeek();
            calendar.setDays(String.valueOf(daysOfWeek).toLowerCase());
            calendar.setType(weekend ? WorkingDayType.WEEKEND.getValue() : WorkingDayType.WORKING_DAY.getValue());

            startDate = startDate.plusDays(1);
            calendarSet.add(calendar);
        }

        // Check if day already exists in calendar
        // There was an error: param in !existedDays.contains() was days, instead of days.getDate().
        // That is the problem, because existedDays is list of existedDays is list of Date objects not Calendar objects
        List<Calendar> diffDays = calendarSet.stream().filter(days -> !existedDays.contains(days.getDate())).collect(Collectors.toList());

        try {
            calendarRepository.saveAll(diffDays);
        } catch (CustomException e) {
            throw new CustomException(e.getMessage());
        }

    }

    /**
     * Read all from Calendar table
     *
     * @return ResponseEntity<List<CalendarResponse>>
     */
    public ResponseEntity<List<CalendarResponse>> read() {
        List<CalendarResponse> result = listConverter(calendarResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    /**
     * Read Calendar by id
     *
     * @param id Integer
     * @return ResponseEntity<CalendarResponse>
     */
    public ResponseEntity<CalendarResponse> readById(Integer id) {
        Optional<CalendarResponse> result = calendarResponseRepository.findById(id);
        return result.map(calendar -> ResponseEntity.ok().body(calendar)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Read Calendar by type
     * @param type String
     * @return ResponseEntity<List<CalendarResponse>>
     */
    public ResponseEntity<List<CalendarResponse>> readAllByType(String type) {
        WorkingDayType workingDayType = WorkingDayType.valueOf(type);
        List<CalendarResponse> result = listConverter(calendarResponseRepository.findAllByType(workingDayType));
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    /**
     * Read Calendar by date
     *
     * @param date Date
     * @return ResponseEntity<CalendarResponse>
     */
    public ResponseEntity<CalendarResponse> readByDate(Date date) {
        Optional<CalendarResponse> result = calendarResponseRepository.findByDate(date);
        return result.map(calendar -> ResponseEntity.ok().body(calendar)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Update Calendar by id
     *
     * @param id Integer
     * @param model Calendar
     * @return ResponseEntity<String>
     */
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

    /**
     * Update Calendar by type
     *
     * @param id Integer
     * @param type String
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> updateByType(Integer id, String type, String description) {
        WorkingDayType workingDayType = getWorkingDayType(type);
        Optional<CalendarResponse> result = calendarResponseRepository.findById(id);
        CalendarResponse model = result.orElseThrow(() -> new CustomException("Day with " + id + "does not exist in calendar"));
        try {
            model.setId(id);
            model.setType(workingDayType.getValue());
            model.setDescription(description);
            System.out.println("working day type modela: " + model.getType());
            calendarResponseRepository.save(model);
            return ResponseEntity.ok().body("Successfully updated");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * Delete Calendar by id
     *
     * @param id Integer
     * @return ResponseEntity<String>
     */
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

    /**
     * Find Calendar by year
     * @param year Integer
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> findByYear(Integer year) {
        LocalDate startDate = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(year, Month.DECEMBER, 31);

        if (calendarResponseRepository.findByDateBetween(AllHelpers.convertToDateViaInstant(startDate), AllHelpers.convertToDateViaInstant(endDate)).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body("Successfully found");
    }

    /**
     * Get WorkingDayType for every day
     *
     * @param type String
     * @return WorkingDayType
     */
    private WorkingDayType getWorkingDayType(String type) {
        WorkingDayType workingDayType;
        switch (type) {
            case "RELIGIOUS_HOLIDAY" -> workingDayType = WorkingDayType.RELIGIOUS_HOLIDAY;
            case "WORKING_DAY" -> workingDayType = WorkingDayType.WORKING_DAY;
            case "NATIONAL_HOLIDAY" -> workingDayType = WorkingDayType.NATIONAL_HOLIDAY;
            default -> throw new CustomException("Invalid type");
        }
        System.out.println(workingDayType);
        return workingDayType;
    }


}
