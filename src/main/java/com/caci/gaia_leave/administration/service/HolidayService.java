package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.Calendar;
import com.caci.gaia_leave.administration.model.request.UserUsedFreeDays;
import com.caci.gaia_leave.administration.model.response.UserUsedFreeDaysResponse;
import com.caci.gaia_leave.administration.repository.request.CalendarRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.caci.gaia_leave.shared_tools.model.WorkingDayType.NATIONAL_HOLIDAY;
import static com.caci.gaia_leave.shared_tools.model.WorkingDayType.WEEKEND;

@Service
@RequiredArgsConstructor
public class HolidayService {
    private final CalendarRepository calendarRepository;

    public ResponseEntity<String> createHolidays(List<UserUsedFreeDaysResponse> usersUsedFreeDays) {
        List<Integer> holidayIds = usersUsedFreeDays.stream().map(UserUsedFreeDaysResponse::getCalendarId).toList();
        if(holidayIds.size() != usersUsedFreeDays.size()) {
            throw new CustomException("Holiday IDs size mismatch");
        }
        holidayIds.forEach(holidayId -> {
            Optional<Calendar> calendar = calendarRepository.findById(holidayId);
            if(calendar.isEmpty()) {
                throw new CustomException("Day not found in calendar");
            }
            if(calendar.get().getType() != WEEKEND) {
                calendar.get().setType(NATIONAL_HOLIDAY);
            }
            try{
                calendarRepository.save(calendar.get());
            }catch(Exception e){
                throw new CustomException(e.getMessage());
            }
        });

        return ResponseEntity.ok().body("Successfully update");
    }

}
