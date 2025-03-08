package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.Calendar;
import com.caci.gaia_leave.administration.model.request.UserUsedFreeDays;
import com.caci.gaia_leave.administration.model.response.UserUsedFreeDaysResponse;
import com.caci.gaia_leave.administration.repository.request.CalendarRepository;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import com.caci.gaia_leave.administration.repository.request.UserUsedFreeDaysRepository;
import com.caci.gaia_leave.administration.repository.response.UserUsedFreeDaysResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import com.caci.gaia_leave.shared_tools.model.WorkingDayType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserUsedFreeDaysService {

    private final UserUsedFreeDaysRepository userUsedFreeDaysRepository;
    private final UserUsedFreeDaysResponseRepository userUsedFreeDaysResponseRepository;
    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;

    public ResponseEntity<UserUsedFreeDaysResponse> create(UserUsedFreeDays model) {

        if (userUsedFreeDaysRepository.existsByUserIdAndCalendarId(model.getUserId(), model.getCalendarId())) {
            throw new CustomException("User and calendar needs to be unique");
        }

        if (!calendarRepository.existsById(model.getCalendarId())) {
            throw new CustomException("Calendar with id: `" + model.getCalendarId() + "` not found");
        }

        if (!userRepository.existsById(model.getUserId())) {
            throw new CustomException("User with id: `" + model.getUserId() + "` not found");
        }

        if (weekendChecker(model)) {
            throw new CustomException("This day is a weekend in calendar");
        }

        try {
            userUsedFreeDaysRepository.save(model);
            Optional<UserUsedFreeDaysResponse> result = userUsedFreeDaysResponseRepository.findById(model.getUserId());
            return result
                    .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                    .orElseGet(() -> ResponseEntity.noContent().build());

        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

    }

    public ResponseEntity<List<UserUsedFreeDaysResponse>> read() {
        List<UserUsedFreeDaysResponse> result = AllHelpers.listConverter(userUsedFreeDaysResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public ResponseEntity<UserUsedFreeDaysResponse> readById(Integer id) {

        Optional<UserUsedFreeDaysResponse> result = userUsedFreeDaysResponseRepository.findById(id);

        return result
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public ResponseEntity<String> update(Integer id, UserUsedFreeDays model) {
        if (!userUsedFreeDaysRepository.existsById(id)) {
            throw new CustomException("Column by id: `" + id + "` not found");
        }

        Optional<UserUsedFreeDaysResponse> unique = userUsedFreeDaysResponseRepository.findByUserIdAndCalendarId(model.getUserId(), model.getCalendarId());
        if (unique.isPresent() && !unique.get().getUserId().equals(id)) {
            throw new CustomException("User and calendar needs to be unique");
        }

        if (!calendarRepository.existsById(model.getCalendarId())) {
            throw new CustomException("Calendar with id: `" + model.getCalendarId() + "` not found");
        }

        if (!userRepository.existsById(model.getUserId())) {
            throw new CustomException("User with id: `" + model.getUserId() + "` not found");
        }

        if (weekendChecker(model)) {
            throw new CustomException("This day is a weekend in calendar");
        }

        try {
            model.setId(id);
            userUsedFreeDaysRepository.save(model);
            return ResponseEntity.ok().body("Successfully updated");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> delete(Integer id) {
        if (!userUsedFreeDaysRepository.existsById(id)) {
            throw new CustomException("Column by id: `" + id + "` not found");
        }

        try {
            userUsedFreeDaysRepository.deleteById(id);
            return ResponseEntity.ok().body("Successfully deleted");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public boolean weekendChecker(UserUsedFreeDays model){
        Optional<Calendar> weekendCheck = calendarRepository.findById(model.getCalendarId());
        return weekendCheck.isPresent() && weekendCheck.get().getType().equals(WorkingDayType.WEEKEND);
    }

}