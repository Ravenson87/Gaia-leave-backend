package com.caci.gaia_leave.administration.service;

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

    public ResponseEntity<String> freeDaysRequest(List<FreeDaysBooking> freeDaysBooking) {
        List<FreeDaysBooking> workingDays = new ArrayList<>();
        List<Integer> calendarIds = listConverter(calendarRep.findAll()).stream().map(Calendar::getId).toList();
//        List<Integer> freeDaysBookingCalendarIds = freeDaysBooking.stream().map(FreeDaysBooking::getCalendarId).toList();
        Set<Integer> userIds = freeDaysBooking.stream().map(FreeDaysBooking::getUserId).collect(Collectors.toSet());
        if (userIds.size() != 1 && userIds.isEmpty()) {
            throw new CustomException("Free Days Booking must have a single user");
        }

        boolean checkUser = userRepository.existsById(userIds.stream().findFirst().get());
        if (!checkUser) {
            throw new CustomException("User not found");
        }
        freeDaysBooking.forEach(model -> {
           if(!calendarIds.contains(model.getCalendarId())) {
               throw new CustomException("Calendar not found");
           }
           if(Objects.equals(model.getCalendar().getType().getValue(), "WEEKEND") || Objects.equals(model.getCalendar().getType().getValue(), "NATIONAL_HOLIDAY")){
               throw new CustomException("This day is already non working day");
           }
           //Pitaj za konstante Milicu ili Zorana!!!
           model.setStatus(0);
            workingDays.add(model);
        });

        try{
            freeDaysBookingRepository.saveAll(workingDays);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");

        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }

//        List<Integer> diffCalendarIds = calendarIds.stream().filter(calendarId -> !freeDaysBookingCalendarIds.contains(calendarId)).toList();

    }
}
