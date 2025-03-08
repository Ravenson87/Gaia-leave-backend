package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.UserTotalAttendance;
import com.caci.gaia_leave.administration.model.response.RoleResponse;
import com.caci.gaia_leave.administration.model.response.UserTotalAttendanceResponse;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import com.caci.gaia_leave.administration.repository.request.UserTotalAttendanceRepository;
import com.caci.gaia_leave.administration.repository.response.UserTotalAttendanceResponseRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import com.caci.gaia_leave.shared_tools.helper.AllHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserTotalAttendanceService {

    private final UserTotalAttendanceRepository userTotalAttendanceRepository;
    private final UserTotalAttendanceResponseRepository userTotalAttendanceResponseRepository;
    private final UserRepository userRepository;

    public ResponseEntity<UserTotalAttendanceResponse> create(UserTotalAttendance model) {
        if (!userRepository.existsById(model.getUserId())) {
            throw new CustomException("User with id: `" + model.getUserId() + "` not found");
        }
        try {
            UserTotalAttendance save = userTotalAttendanceRepository.save(model);
            Optional<UserTotalAttendanceResponse> result = userTotalAttendanceResponseRepository.findById(save.getId());
            return result
                    .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                    .orElseGet(() -> ResponseEntity.noContent().build());

        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<List<UserTotalAttendanceResponse>> read() {
        List<UserTotalAttendanceResponse> result = AllHelpers.listConverter(userTotalAttendanceResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    public ResponseEntity<UserTotalAttendanceResponse> readById(Integer id) {
        Optional<UserTotalAttendanceResponse> result = userTotalAttendanceResponseRepository.findById(id);

        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public ResponseEntity<String> update(Integer id, UserTotalAttendance model) {

        if (!userTotalAttendanceRepository.existsById(id)) {
            throw new CustomException("User Total Attendance With ID Not Found");
        }
        Optional<UserTotalAttendanceResponse> unique = userTotalAttendanceResponseRepository.findByUserId(model.getUserId());
        if (unique.isPresent() && !unique.get().getId().equals(id)) {
            throw new CustomException("User Total Attendance `" + model.getUserId() + "` already exists.");
        }
        if (!userRepository.existsById(model.getUserId())) {
            throw new CustomException("User with id: `" + model.getUserId() + "` not found");
        }

        try {
            model.setId(id);
            userTotalAttendanceRepository.save(model);
            return ResponseEntity.ok().body("Successfully update");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<String> delete(Integer id) {
        if (!userTotalAttendanceRepository.existsById(id)) {
            throw new CustomException("User Total Attendance With ID Not Found");
        }

        try {
            userTotalAttendanceRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
}
