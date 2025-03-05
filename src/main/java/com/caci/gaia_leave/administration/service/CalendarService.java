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

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.listConverter;

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


    public ResponseEntity<List<Calendar>> read(){
        List<Calendar> result = listConverter(calendarRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);

    }

    public ResponseEntity<Calendar> readById(Integer id) {
        Optional<Calendar> result = calendarRepository.findById(id);
        return result.map(calendar -> ResponseEntity.ok().body(calendar)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<List<Calendar>> readAllByType(String type) {
        WorkingDayType workingDayType = WorkingDayType.valueOf(type);
        List<Calendar> result = listConverter(calendarRepository.findAllByType(workingDayType));
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    public ResponseEntity<Calendar> readByDate(Date date) {
        Optional<Calendar> result = calendarRepository.findByDate(date);
        return result.map(calendar -> ResponseEntity.ok().body(calendar)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<String> update(Integer id, Calendar model) {
        if(!calendarRepository.existsById(id)) {
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
        Optional<Calendar> result = calendarRepository.findById(id);
        Calendar model = result.orElseThrow(() -> new CustomException("Day with " + id + "does not exist in calendar"));
        try {
            model.setType(workingDayType);
            calendarRepository.save(model);
            return ResponseEntity.ok().body("Successfully updated");
        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> delete(Integer id) {
        if(!calendarRepository.existsById(id)) {
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
        if(!calendarRepository.existsByDate(AllHelpers.convertToDateViaInstant(localDate))){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body("Successfully found");
    }


    public static WorkingDayType getWorkingDayType(String type) {
        return switch (type) {
            case "weekend" -> WorkingDayType.WEEKEND;
            case "weekday" -> WorkingDayType.WORKING_DAY;
            case "national_holiday" -> WorkingDayType.NATIONAL_HOLIDAY;
            default -> throw new CustomException("Invalid type");
        };
    }


}
