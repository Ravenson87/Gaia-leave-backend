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

    /**
     * Assign OvertimeHours to User and convert overtime hours to free days or payment
     *
     * @param userId Integer
     * @param overTimeWorkingHours Integer
     * @param asFreeDays boolean
     * @return ResponseEntity<String>
     */
    public ResponseEntity<String> assignOvertimeHours(Integer userId, Integer overTimeWorkingHours, boolean asFreeDays) {
        // Check does user with given id exists
        Optional<OvertimeHours> overtimeHoursEntity = overtimeHoursRepository.findById(userId);
        if (overtimeHoursEntity.isEmpty()) {
            throw new CustomException("User id " + userId + " not found");
        }
        Optional<UserTotalAttendance> userTotalAttendance = userTotalAttendanceRepository.findByUserId(userId);
        if (userTotalAttendance.isEmpty()) {
            throw new CustomException("User id " + userId + " not found");
        }

        // Transform working hours in free days, and count how much hours remain
        Integer userTotalWorkingHours = userTotalAttendance.get().getTotalWorkingHours();
        int freeDays = overTimeWorkingHours / userTotalWorkingHours;
        Integer remainderWorkingHours = overTimeWorkingHours % userTotalWorkingHours;

        if(overTimeWorkingHours <= 0) {
            throw new CustomException("User dont have overtime hours");
        }

        // Count how much hours to subtract from overtime hours
        int totalFreeDays = userTotalAttendance.get().getTotalFreeDays();
        int hoursToSubtractAsDays = freeDays * userTotalAttendance.get().getTotalWorkingHours();
        int overtimeHoursToSubtract = userTotalAttendance.get().getOvertimeHoursSum() - hoursToSubtractAsDays;

        // Check if hours are transform to days or payment, and then update overtime hours and free days if needed
        if(asFreeDays) {
            if (overTimeWorkingHours < userTotalAttendance.get().getTotalWorkingHours()) {
                throw new CustomException(remainderWorkingHours + " is not enough overtime working hours for free day");
            }
            try {
                userTotalAttendance.get().setTotalFreeDays(totalFreeDays + freeDays);
                userTotalAttendance.get().setOvertimeHoursSum(overtimeHoursToSubtract);
                userTotalAttendanceRepository.save(userTotalAttendance.get());

            } catch (Exception e) {
                throw new CustomException(e.getMessage());
            }
        } else {
            userTotalAttendance.get().setOvertimeHoursSum(userTotalAttendance.get().getOvertimeHoursSum() - overTimeWorkingHours);
            userTotalAttendanceRepository.save(userTotalAttendance.get());
        }

        return ResponseEntity.ok().body("Successfully updated");
    }

    /**
     * Sum overtime working hours
     *
     * @param userId Integer
     * @param startDate String
     * @param endDate String
     * @return ResponseEntity<Integer>
     */
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
