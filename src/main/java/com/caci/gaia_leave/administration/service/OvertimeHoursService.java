package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.OvertimeHours;
import com.caci.gaia_leave.administration.model.response.OvertimeHoursResponse;
import com.caci.gaia_leave.administration.repository.request.CalendarRepository;
import com.caci.gaia_leave.administration.repository.request.OvertimeHoursRepository;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import com.caci.gaia_leave.administration.repository.response.OvertimeHoursResponseRepository;
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
public class OvertimeHoursService {
    private final OvertimeHoursRepository overtimeHoursRepository;
    private final OvertimeHoursResponseRepository overtimeHoursResponseRepository;
    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;

    public ResponseEntity<OvertimeHoursResponse> create(OvertimeHours model){
        if(overtimeHoursRepository.existsByUserIdAndCalendarId(model.getUserId(), model.getCalendarId())){
            throw new CustomException("Overtime hours for that date and that user already exists.");
        }
        try{
            overtimeHoursRepository.save(model);
            Optional<OvertimeHoursResponse> result = overtimeHoursResponseRepository.findById(model.getId());
            return result
                    .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                    .orElseGet(() -> ResponseEntity.noContent().build());
        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    public ResponseEntity<List<OvertimeHoursResponse>> read(){
        List<OvertimeHoursResponse> result = AllHelpers.listConverter(overtimeHoursResponseRepository.findAll());
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(result);
    }

    public ResponseEntity<OvertimeHoursResponse> readById(Integer id){
        Optional<OvertimeHoursResponse> result = overtimeHoursResponseRepository.findById(id);
        return result
                .map(response -> ResponseEntity.ok().body(response))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    public ResponseEntity<String> update(Integer id, OvertimeHours model) {
        if (!overtimeHoursResponseRepository.existsById(id)) {
            throw new CustomException("Overtime hours for that date and that user does not exist.");
        }
        Optional<OvertimeHoursResponse> unique = overtimeHoursResponseRepository.findByUserIdAndCalendarId(model.getUserId(), model.getCalendarId());
        if(unique.isPresent() && !unique.get().getId().equals(id)){
            throw new CustomException("Overtime hours for that date and that user does not exists.");
        }
        if(!userRepository.existsById(model.getUserId())){
            throw new CustomException("User Id " + model.getUserId() + " does not exist.");
        }
        if(!calendarRepository.existsById(model.getCalendarId())){
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

    public ResponseEntity<HttpStatus> delete(Integer id) {
        if(!overtimeHoursResponseRepository.existsById(id)){
            throw new CustomException("Overtime hours for that date and that user does not exist.");
        }
        try{
            overtimeHoursResponseRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
}
