package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.OvertimeHours;
import com.caci.gaia_leave.administration.model.request.UserTotalAttendance;
import com.caci.gaia_leave.administration.repository.request.CalendarRepository;
import com.caci.gaia_leave.administration.repository.request.OvertimeHoursRepository;
import com.caci.gaia_leave.administration.repository.request.UserTotalAttendanceRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.convertedStringToDate;


@Service
@RequiredArgsConstructor
public class WorkingHoursService {

    private final UserTotalAttendanceRepository userTotalAttendanceRepository;
    private final OvertimeHoursRepository overtimeHoursRepository;
    private final CalendarRepository calendarRepository;

    public ResponseEntity<String> assignOvertimeAsFreeDays(Integer userId, Integer workingHours) {
        Optional<OvertimeHours> overtimeHoursEntity = overtimeHoursRepository.findById(userId);
        if (overtimeHoursEntity.isEmpty()) {
            throw new CustomException("User id " + userId + " not found");
        }
        Optional<UserTotalAttendance> userTotalAttendance = userTotalAttendanceRepository.findByUserId(userId);
        if (userTotalAttendance.isEmpty()) {
            throw new CustomException("User id " + userId + " not found");
        }

        Integer userTotalWorkingHours = userTotalAttendance.get().getTotalWorkingHours();
        int freeDays = workingHours / userTotalWorkingHours;
        Integer remainderWorkingHours = workingHours % userTotalWorkingHours;
        if (workingHours < 8) {
            throw new CustomException(remainderWorkingHours + " is not enough workingHours for free day");
        }

        int totalFreeDays = userTotalAttendance.get().getTotalFreeDays();
        int hoursToSubtract = freeDays * userTotalAttendance.get().getTotalWorkingHours();
        int overtimeHoursToSubtract = userTotalAttendance.get().getOvertimeHoursSum() - hoursToSubtract;
        try {
            userTotalAttendance.get().setTotalFreeDays(totalFreeDays + freeDays);
            userTotalAttendance.get().setOvertimeHoursSum(overtimeHoursToSubtract);
            userTotalAttendanceRepository.save(userTotalAttendance.get());

        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

        return ResponseEntity.ok().body("Successfully updated");
    }


    public ResponseEntity<Integer> sumOvertimeWorkingHours(Integer userId, String startDate, String endDate) {
        if(!overtimeHoursRepository.existsByUserId(userId)) {
            throw new CustomException("User id " + userId + " does not exists");
        }
        Date startDateToDate = convertedStringToDate(startDate);

        if(!calendarRepository.existsByDate(startDateToDate)) {
            throw new CustomException("Start date " + startDate + " does not exist");
        }

        Date endDateToDate = convertedStringToDate(endDate);
        if(!calendarRepository.existsByDate(endDateToDate)) {
            throw new CustomException("End date " + endDate + " does not exist");
        }

        Integer overtimeHoursSum = overtimeHoursRepository.sumOvertimeWorkingHours(userId, startDate, endDate);
        return ResponseEntity.ok().body(overtimeHoursSum);
    }
}
