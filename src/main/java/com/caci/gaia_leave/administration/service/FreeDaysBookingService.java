package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.dto.FreeDaysBookingDTO;
import com.caci.gaia_leave.administration.model.dto.FreeDaysBookingUpdateDTO;
import com.caci.gaia_leave.administration.model.request.Calendar;
import com.caci.gaia_leave.administration.model.request.FreeDaysBooking;
import com.caci.gaia_leave.administration.model.response.FreeDaysBookingResponse;
import com.caci.gaia_leave.administration.repository.request.CalendarRepository;
import com.caci.gaia_leave.administration.repository.request.FreeDaysBookingRepository;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import com.caci.gaia_leave.administration.repository.response.FreeDaysBookingResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.listConverter;

@Service
@RequiredArgsConstructor
public class FreeDaysBookingService {

    private final FreeDaysBookingRepository freeDaysBookingRepository;
    private final FreeDaysBookingResponseRepository freeDaysBookingResponseRepository;
    private final CalendarRepository calendarRep;
    private final UserRepository userRepository;

    private final int IN_PROGRESS = 0;
    /**
     * Create free days booking by user request.
     *
     * @param freeDaysBooking List<FreeDaysBookingDTO>
     * @param userId          Integer
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> freeDaysRequest(List<FreeDaysBookingDTO> freeDaysBooking, Integer userId) {
        List<FreeDaysBooking> workingDays = new ArrayList<>();
        List<Integer> freeDaysCalendarIds = freeDaysBooking.stream().map(FreeDaysBookingDTO::getCalendarId).collect(Collectors.toList());
        // Retrieving the list of calendars that match the specified calendar id and type WORKING_DAY with native query.
        List<Calendar> calendar = calendarRep.calendarRequestByIdsAndType(freeDaysCalendarIds, "WORKING_DAY");
        List<Integer> calendarIds = calendar.stream().map(Calendar::getId).toList();
        if (userId == null) {
            throw new CustomException("User must be provided");
        }

        boolean checkUser = userRepository.existsById(userId);
        if (!checkUser) {
            throw new CustomException("User not found");
        }
        // We are iterating through the input data to verify if a calendar with the specified ID exists.
        freeDaysBooking.forEach(model -> {
            FreeDaysBooking modelForWorkingDays = new FreeDaysBooking();
            // if a match is found, we add to the model.
            if (calendarIds.contains(model.getCalendarId())) {
                modelForWorkingDays.setCalendarId(model.getCalendarId());
                modelForWorkingDays.setUserId(userId);
                modelForWorkingDays.setDescription(model.getDescription());
                modelForWorkingDays.setStatus(IN_PROGRESS);
                workingDays.add(modelForWorkingDays);
            }
        });
        try {
            freeDaysBookingRepository.saveAll(workingDays);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");

        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

    /**
     * Read all from FreeDaysBooking table
     *
     * @return ResponseEntity<List<FreeDaysBookingResponse>>
     */
    public ResponseEntity<List<FreeDaysBookingResponse>> read(){
        List<FreeDaysBookingResponse> result = AllHelpers.listConverter(freeDaysBookingResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Read free days by status
     *
     * @param status Integer
     * @return ResponseEntity<List < FreeDaysBookingResponse>>
     */
    public ResponseEntity<List<FreeDaysBookingResponse>> readByStatus(Integer status) {
        List<FreeDaysBookingResponse> result = freeDaysBookingResponseRepository.readByStatus(status);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Read free days by calendar id
     *
     * @param calendarId Integer
     * @return ResponseEntity<List < FreeDaysBookingResponse>>
     */
    public ResponseEntity<List<FreeDaysBookingResponse>> readByCalendarId(Integer calendarId) {
        List<FreeDaysBookingResponse> result = freeDaysBookingResponseRepository.readByCalendarId(calendarId);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Read free days by user id
     *
     * @param userId Integer
     * @return ResponseEntity<List < FreeDaysBookingResponse>>
     */
    public ResponseEntity<List<FreeDaysBookingResponse>> readByUserId(Integer userId) {
        List<FreeDaysBookingResponse> result = freeDaysBookingResponseRepository.readByUserId(userId);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Read by date range and status
     *
     * @param startDate Date
     * @param endDate   Date
     * @param status     String
     * @return ResponseEntity<List < FreeDaysBookingResponse>>
     */
    public ResponseEntity<List<FreeDaysBookingResponse>> readByDateRangeAndStatus(String startDate, String endDate, Integer status) {
        List<FreeDaysBookingResponse> result = freeDaysBookingResponseRepository.freeDaysTypeRequestByDateRangeAndStatus(startDate, endDate, status);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Approve or reject free days request.
     *
     * @param freeDaysBookingUpdateDTO List<FreeDaysBookingUpdateDTO>
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> freeDaysAcceptance(List<FreeDaysBookingUpdateDTO> freeDaysBookingUpdateDTO) {
        // Get ids from input parameter
        List<Integer> freeDaysCalendarIds = freeDaysBookingUpdateDTO.stream().map(FreeDaysBookingUpdateDTO::getId).toList();
        // Find free days booking response by ids and saved in list
        List<FreeDaysBookingResponse> freeDaysBooking = listConverter(freeDaysBookingResponseRepository.findAllById(freeDaysCalendarIds));
        if (freeDaysBooking.isEmpty()) {
            throw new CustomException("FreeDaysBooking not found");
        }

        // Update status for each found request recorded in the corresponding DTO object
        List<FreeDaysBookingResponse> updatedBookings = freeDaysBooking.stream()
                .map(freeDaysBookingResponse -> {
                    freeDaysBookingResponse.setStatus(freeDaysBookingUpdateDTO.stream()
                            .filter(freeday -> Objects.equals(freeday.getId(), freeDaysBookingResponse.getId()))
                            .findFirst()
                            .get()
                            .getStatus());
                    return freeDaysBookingResponse;
                })
                .collect(Collectors.toList());
        try {
            freeDaysBookingResponseRepository.saveAll(updatedBookings);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("FreeDays accepted");
    }

}
