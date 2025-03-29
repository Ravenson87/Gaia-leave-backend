package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.Calendar;
import com.caci.gaia_leave.administration.repository.request.CalendarRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.formatDate;


@Service
@RequiredArgsConstructor
public class HolidayService {
    private final CalendarRepository calendarRepository;


    public ResponseEntity<String> updateHolidays(List<Calendar> holidays) {
        //TODO Optimizovati tako sto ce se umesto "findAll()" koristiti "findAllById"
        List<Calendar> calendar = AllHelpers.listConverter(calendarRepository.findAll());

        if(calendar.isEmpty()){
            throw new CustomException("No days in calendar");
        }

        // Kreiramo skup parova (ID, Date) iz prve liste
        Set<Map.Entry<Integer, String>> idDateSet = calendar.stream()
                .map(o -> Map.entry(o.getId(), formatDate(o.getDate())))
                .collect(Collectors.toSet());

        // Filtriramo Kalendare koji postoje u oba skupa sa istim datumom
        List<Calendar> holidaysToSave = holidays.stream()
                .filter(o -> idDateSet.contains(Map.entry(o.getId(), formatDate(o.getDate()))))
                .toList();
        if(holidaysToSave.isEmpty()){
            throw new CustomException("There are no holidays to save");
        }

        try {
            calendarRepository.saveAll(holidaysToSave);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        return ResponseEntity.ok().body("Successfully updated");

    }

}



