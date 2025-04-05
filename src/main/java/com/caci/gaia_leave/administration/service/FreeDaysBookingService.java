package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.response.CalendarResponse;
import com.caci.gaia_leave.administration.model.response.FreeDaysBookingResponse;
import com.caci.gaia_leave.administration.repository.request.CalendarRepository;
import com.caci.gaia_leave.administration.repository.request.FreeDaysBookingRepository;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import com.caci.gaia_leave.administration.repository.response.FreeDaysBookingResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.model.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.listConverter;

@Service
@RequiredArgsConstructor
public class FreeDaysBookingService {

    private final FreeDaysBookingRepository freeDaysBookingRepository;
    private final FreeDaysBookingResponseRepository freeDaysBookingResponseRepository;
    private final CalendarRepository calendarRep;
    private final UserRepository userRepository;

    public ResponseEntity<String> freeDaysRequest(List<FreeDaysBookingResponse> freeDaysBookingResponses) {
        List<Date> workingDays = new ArrayList<>();
        List<Integer> calendarIds = listConverter(calendarRep.findAll()).stream().map(calendar -> calendar.getId()).collect(Collectors.toList());
        List<Integer> freeDaysBookingCalendarIds = freeDaysBookingResponses.stream().map(freeDaysBookingResponse -> freeDaysBookingResponse.getCalendarId()).collect(Collectors.toList());
        Set<Integer> userIds = freeDaysBookingResponses.stream().map(FreeDaysBookingResponse::getUserId).collect(Collectors.toSet());
        if (userIds.size() != 1 && userIds.isEmpty()) {
            throw new CustomException("Free Days Booking must have a single user");
        }

        boolean checkUser = userRepository.existsById(userIds.stream().findFirst().get());
        if (!checkUser) {
            throw new CustomException("User not found");
        }

        List<Integer> diffCalendarIds = calendarIds.stream().filter(calendarId -> !freeDaysBookingCalendarIds.contains(calendarId)).toList();

        return null;
    }
}
