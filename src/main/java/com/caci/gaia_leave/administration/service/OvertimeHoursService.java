package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.OvertimeHours;
import com.caci.gaia_leave.administration.model.request.UserTotalAttendance;
import com.caci.gaia_leave.administration.model.response.OvertimeHoursResponse;
import com.caci.gaia_leave.administration.repository.request.CalendarRepository;
import com.caci.gaia_leave.administration.repository.request.OvertimeHoursRepository;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import com.caci.gaia_leave.administration.repository.request.UserTotalAttendanceRepository;
import com.caci.gaia_leave.administration.repository.response.OvertimeHoursResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OvertimeHoursService {
    private final OvertimeHoursRepository overtimeHoursRepository;
    private final OvertimeHoursResponseRepository overtimeHoursResponseRepository;
    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;
    private final UserTotalAttendanceRepository userTotalAttendanceRepository;
    private final ObjectMapper objectMapper;

    /**
     * Create OvertimeHours in database
     *
     * @param models List<OvertimeHours>
     * @return ResponseEntity<List < OvertimeHoursResponse>>
     */
    public ResponseEntity<List<OvertimeHoursResponse>> create(List<OvertimeHours> models) {
        List<OvertimeHours> save = new ArrayList<>();

        models.forEach(model -> {
            if (overtimeHoursRepository.existsByUserIdAndCalendarId(model.getUserId(), model.getCalendarId())) {
                throw new CustomException("Overtime hours for that date and that user already exists.");
            }

            if (!userRepository.existsById(model.getUserId())) {
                throw new CustomException("User Id " + model.getUserId() + " does not exist.");
            }
            if (!calendarRepository.existsById(model.getCalendarId())) {
                throw new CustomException("Calendar Id " + model.getCalendarId() + " does not exist.");
            }

            Optional<UserTotalAttendance> userTotalAttendance = userTotalAttendanceRepository.findByUserId(model.getUserId());
            if (userTotalAttendance.isEmpty()) {
                throw new CustomException("User Id " + model.getUserId() + " does not exist.");
            }
            if(userTotalAttendance.get().getTotalWorkingHours() == null){
                throw new CustomException("Total working hours for " + model.getUserId() + " is not set.");
            }
            int maxOvertimeHours = 24 - userTotalAttendance.get().getTotalWorkingHours();

            if (model.getOvertimeHours() > maxOvertimeHours) {
                throw new CustomException("Overtime hours can not exceed 24 hours.");
            }
            save.add(model);

        });

        try {
            //Save models in database
            overtimeHoursRepository.saveAll(models);
            //Get all ids from list of OvertimeHours models and check is it saved in database
            List<Integer> ids = new ArrayList<>();
            save.forEach(model -> ids.add(model.getId()));
            List<OvertimeHoursResponse> result = AllHelpers.listConverter(overtimeHoursResponseRepository.findAllById(ids));
            return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.OK).body(result);

        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * Read OvertimeHours from database
     *
     * @return ResponseEntity<List < OvertimeHoursResponse>>
     */
    public ResponseEntity<List<OvertimeHoursResponse>> read() {
        List<OvertimeHoursResponse> result = AllHelpers.listConverter(overtimeHoursResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    /**
     * @param id Integer
     * @return ResponseEntity<OvertimeHoursResponse>
     */
    public ResponseEntity<OvertimeHoursResponse> readById(Integer id) {
        Optional<OvertimeHoursResponse> result = overtimeHoursResponseRepository.findById(id);
        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    /**
     * Update OvertimeHours in database
     *
     * @param id    Integer
     * @param model OvertimeHours
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> update(Integer id, OvertimeHours model) {
        if (!overtimeHoursResponseRepository.existsById(id)) {
            throw new CustomException("Overtime hours for that date and that user does not exist.");
        }
        Optional<OvertimeHoursResponse> unique = overtimeHoursResponseRepository.findByUserIdAndCalendarId(model.getUserId(), model.getCalendarId());
        if (unique.isPresent() && !unique.get().getId().equals(id)) {
            throw new CustomException("Overtime hours for that date and that user does not exists.");
        }
        if (!userRepository.existsById(model.getUserId())) {
            throw new CustomException("User Id " + model.getUserId() + " does not exist.");
        }
        if (!calendarRepository.existsById(model.getCalendarId())) {
            throw new CustomException("Calendar Id " + model.getCalendarId() + " does not exist.");
        }
        try {
            model.setId(id);
            overtimeHoursRepository.save(model);
            return ResponseEntity.ok().body("Successfully update");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * Delete OvertimeHours by id from database
     *
     * @param id Integer
     * @return ResponseEntity<HttpStatus>
     */
    public ResponseEntity<HttpStatus> delete(Integer id) {
        if (!overtimeHoursResponseRepository.existsById(id)) {
            throw new CustomException("Overtime hours for that date and that user does not exist.");
        }
        try {
            overtimeHoursResponseRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * Update overtimeCompensation
     *
     * @param models List<OvertimeHoursResponse>
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> updateOvertimeCompensation(List<OvertimeHoursResponse> models) {
        List<OvertimeHours> save = new ArrayList<>();
        // Find overtimeHours by userId and calendarId and add it to the list
        models.forEach(model -> {
            if (!overtimeHoursRepository.existsByUserIdAndCalendarId(model.getUserId(), model.getCalendarId())) {
                throw new CustomException("Overtime hours for that date and that user does not exist.");
            }

            // Map OvertimeHoursResponse to OvertimeHours with ObjectMapper
            OvertimeHours overtimeHours = objectMapper.convertValue(model, OvertimeHours.class);

            //Set id, because OvertimeHoursResponse doesnt have id value
            overtimeHours.setId(model.getId());
            save.add(overtimeHours);
        });
        // Save overtimeHours in database
        try {
            overtimeHoursRepository.saveAll(save);
            return ResponseEntity.ok().body("Successfully update");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

}
