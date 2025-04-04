package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.response.FreeDaysBookingResponse;
import com.caci.gaia_leave.administration.repository.request.FreeDaysBookingRepository;
import com.caci.gaia_leave.administration.repository.response.FreeDaysBookingResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FreeDaysBookingService {

    private final FreeDaysBookingRepository freeDaysBookingRepository;
    private final FreeDaysBookingResponseRepository freeDaysBookingResponseRepository;
    private final CalendarService calendarService;

    public ResponseEntity<String> freeDaysRequest(List<FreeDaysBookingResponse> freeDaysBookingResponses) {
        List<Date> workingDays = new ArrayList<>();

        return null;
    }
}
