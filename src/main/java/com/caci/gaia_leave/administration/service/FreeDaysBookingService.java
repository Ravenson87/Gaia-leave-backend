package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.dto.FreeDaysBookingDTO;
import com.caci.gaia_leave.administration.model.request.Calendar;
import com.caci.gaia_leave.administration.model.request.FreeDaysBooking;
import com.caci.gaia_leave.administration.model.response.CalendarResponse;
import com.caci.gaia_leave.administration.model.response.FreeDaysBookingResponse;
import com.caci.gaia_leave.administration.repository.request.CalendarRepository;
import com.caci.gaia_leave.administration.repository.request.FreeDaysBookingRepository;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import com.caci.gaia_leave.administration.repository.response.FreeDaysBookingResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.model.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.listConverter;

@Service
@RequiredArgsConstructor
public class FreeDaysBookingService {

    private final FreeDaysBookingRepository freeDaysBookingRepository;
    private final FreeDaysBookingResponseRepository freeDaysBookingResponseRepository;
    private final CalendarRepository calendarRep;
    private final UserRepository userRepository;

    public ResponseEntity<String> freeDaysRequest(List<FreeDaysBookingDTO> freeDaysBooking, Integer userId) {
        List<FreeDaysBooking> workingDays = new ArrayList<>();
        List<Integer> freeDaysCalendarIds = freeDaysBooking.stream().map(FreeDaysBookingDTO::getCalendarId).collect(Collectors.toList());
        List<Calendar> calendar = calendarRep.calendarRequestByIdsAndType(freeDaysCalendarIds, "WORKING_DAY");
        List<Integer> calendarIds = calendar.stream().map(Calendar::getId).toList();
        if (userId == null) {
            throw new CustomException("User must be provided");
        }

        boolean checkUser = userRepository.existsById(userId);
        if (!checkUser) {
            throw new CustomException("User not found");
        }
        FreeDaysBooking modelForWorkingDays = new FreeDaysBooking();
        freeDaysBooking.forEach(model -> {
            if (calendarIds.contains(model.getCalendarId())) {
                modelForWorkingDays.setCalendarId(model.getCalendarId());
                modelForWorkingDays.setUserId(userId);
                modelForWorkingDays.setDescription(model.getDescription());
                modelForWorkingDays.setStatus(0);
                workingDays.add(modelForWorkingDays);
            }
        });
        System.out.println(workingDays);
        try {
//            freeDaysBookingRepository.saveAll(workingDays);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");

        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }
}
