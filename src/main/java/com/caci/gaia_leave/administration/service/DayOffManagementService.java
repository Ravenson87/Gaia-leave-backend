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

    /**
     * Subtract or add free days form UserUsedFreeDays
     *
     * @param usersFreeDays List<UserUsedFreeDays>
     * @param save Boolean
     * @return ResponseEntity<String>
     */
    @Transactional
    public ResponseEntity<String> subtractDaysFromFreeDays(List<UserUsedFreeDays> usersFreeDays, Boolean save) {
        // Get ids from users, and then create map, grouping all the same ids together
        // PITATI ZORANA DA TI JOS JEDNOM OBJASNI OVO GRUPISANJE JUZERA
        List<Integer> usersIds = usersFreeDays.stream().map(UserUsedFreeDays::getUserId).toList();
        Map<Integer, List<Integer>> groups = usersIds.stream().collect(Collectors.groupingBy(num -> num, LinkedHashMap::new, Collectors.toList()));

        //Create List of grouped Ids
        List<List<Integer>> groupedList = new ArrayList<>(groups.values());
        //Create list of models
        List<UserTotalAttendance> models = new ArrayList<>();

        // For each grouped ids get first element, that is model id, and get size, that is quantity of free days
        // If save is true, than saved days are used and subtract from TotalFreeDays, else add free days to TotalFreeDays
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

        // Save changes in TotalAttendance table
        try {
            updateUserUsedFreeDays(usersFreeDays, save);
            userTotalAttendanceRepository.saveAll(models);
        }catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        return ResponseEntity.ok().body("Successfully update");
    }

    /**
     * Update UserUsedFreeDays, if save is true then save, else delete
     *
     * @param userUsedFreeDays List<UserUsedFreeDays>
     * @param save boolean
     */
    public void updateUserUsedFreeDays(List<UserUsedFreeDays> userUsedFreeDays, boolean save) {
        if(save) {
            userUsedFreeDaysRepository.saveAll(userUsedFreeDays);
        } else {
            //TODO optimise this with native query
            userUsedFreeDays.forEach(userUsedFreeDay -> {
                userUsedFreeDaysRepository.deleteByUserIdAndCalendarId(userUsedFreeDay.getUserId(), userUsedFreeDay.getCalendarId());
            });

        }

    }
}


