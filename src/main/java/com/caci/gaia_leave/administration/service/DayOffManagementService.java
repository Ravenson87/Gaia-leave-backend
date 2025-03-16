package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.UserTotalAttendance;
import com.caci.gaia_leave.administration.model.request.UserUsedFreeDays;
import com.caci.gaia_leave.administration.repository.request.UserTotalAttendanceRepository;
import com.caci.gaia_leave.administration.repository.request.UserUsedFreeDaysRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DayOffManagementService {
    private final UserTotalAttendanceRepository userTotalAttendanceRepository;
    private final UserUsedFreeDaysRepository userUsedFreeDaysRepository;

    @Transactional
    public ResponseEntity<String> subtractDaysFromFreeDays(List<UserUsedFreeDays> usersFreeDays, Boolean save) {
        List<Integer> usersIds = usersFreeDays.stream().map(UserUsedFreeDays::getUserId).toList();
        Map<Integer, List<Integer>> groups = usersIds.stream().collect(Collectors.groupingBy(num -> num, LinkedHashMap::new, Collectors.toList()));

        List<List<Integer>> groupedList = new ArrayList<>(groups.values());
        List<UserTotalAttendance> models = new ArrayList<>();

        groupedList.forEach(group -> {
            int userId = group.getFirst();
            int freeDays = group.size();
            Optional<UserTotalAttendance> check = userTotalAttendanceRepository.findByUserId(userId);
            if (check.isEmpty()) {
                throw new CustomException("User with id " + userId + " does not exist");
            }
            if(save) {
                check.get().setTotalFreeDays(check.get().getTotalFreeDays() - freeDays);
            }else{
                check.get().setTotalFreeDays(check.get().getTotalFreeDays() + freeDays);
            }
            models.add(check.get());
        });

        try {
            updateUserUsedFreeDays(usersFreeDays, save);
            userTotalAttendanceRepository.saveAll(models);
        }catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        return ResponseEntity.ok().body("Successfully update");
    }


    public void updateUserUsedFreeDays(List<UserUsedFreeDays> userUsedFreeDays, boolean save) {
        if(save) {
            userUsedFreeDaysRepository.saveAll(userUsedFreeDays);
        } else {
            //TODO optimise this with native querry
            userUsedFreeDays.forEach(userUsedFreeDay -> {
                userUsedFreeDaysRepository.deleteByUserIdAndCalendarId(userUsedFreeDay.getUserId(), userUsedFreeDay.getCalendarId());
            });

        }

    }
}


