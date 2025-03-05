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
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.listConverter;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final CalendarResponseRepository calendarResponseRepository;

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


    public ResponseEntity<List<CalendarResponse>> read(){
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
        if(!calendarResponseRepository.existsById(id)) {
            throw new CustomException("Day with " + id + "does not exist in calendar");
        }
        try{
            calendarRepository.save(model);
            return ResponseEntity.ok().body("Successfully updated");
        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }

    }
    public ResponseEntity<String> updateByType(Integer id, String type) {
        WorkingDayType workingDayType = getWorkingDayType(type);
        Optional<CalendarResponse> result = calendarResponseRepository.findById(id);
        CalendarResponse model = result.orElseThrow(() -> new CustomException("Day with " + id + "does not exist in calendar"));
        try {
            model.setType(workingDayType);
            calendarResponseRepository.save(model);
            return ResponseEntity.ok().body("Successfully updated");
        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> delete(Integer id) {
        if(!calendarResponseRepository.existsById(id)) {
            throw new CustomException("Day with " + id + "does not exist in calendar");
        }
        try{
            calendarRepository.deleteById(id);
            return ResponseEntity.ok().body("Successfully deleted");
        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> findByYear(Integer year) {
        LocalDate localDate = LocalDate.of(year, Month.JANUARY, 1);
        if(!calendarResponseRepository.existsByDate(AllHelpers.convertToDateViaInstant(localDate))){
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
