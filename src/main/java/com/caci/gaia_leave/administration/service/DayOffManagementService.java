package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.UserTotalAttendance;
import com.caci.gaia_leave.administration.model.request.UserUsedFreeDays;
import com.caci.gaia_leave.administration.repository.request.UserTotalAttendanceRepository;
import com.caci.gaia_leave.administration.repository.request.UserUsedFreeDaysRepository;
import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DayOffManagementService {
    private final UserTotalAttendanceRepository userTotalAttendanceRepository;
    private final UserUsedFreeDaysRepository userUsedFreeDaysRepository;
    private final JdbcTemplate jdbcTemplate;

    /**
     * Subtract or add free days form UserUsedFreeDays
     *
     * @param usersFreeDays List<UserUsedFreeDays>
     * @param save          Boolean
     * @return ResponseEntity<String>
     */
    @Transactional
    public ResponseEntity<String> subtractDaysFromFreeDays(List<UserUsedFreeDays> usersFreeDays, Boolean save) {
        // Get ids from users, and then create map, grouping all the same ids together
        // PITATI ZORANA DA TI JOS JEDNOM OBJASNI OVO GRUPISANJE User-a
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
                throw new CustomException("User with id " + userId + " does not exist in UserTotalAttendance");
            }
            if (save) {
                check.get().setTotalFreeDays(check.get().getTotalFreeDays() - freeDays);
            } else {
                check.get().setTotalFreeDays(check.get().getTotalFreeDays() + freeDays);
            }
            models.add(check.get());
        });

        // Save changes in TotalAttendance table
        try {
            updateUserUsedFreeDays(usersFreeDays, save);
            userTotalAttendanceRepository.saveAll(models);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        return ResponseEntity.ok().body("Successfully update");
    }

    /**
     * Update UserUsedFreeDays, if save is true then save, else delete
     *
     * @param userUsedFreeDays List<UserUsedFreeDays>
     * @param save             boolean
     */
    public void updateUserUsedFreeDays(List<UserUsedFreeDays> userUsedFreeDays, boolean save) {
        //Check does function need to save free days or delete free days
        if (save) {
            try {
                userUsedFreeDaysRepository.saveAll(userUsedFreeDays);
            } catch (Exception e) {
                throw new CustomException(e.getMessage());
            }
        } else {

            // Get user_id and calendar id
            Map<Integer, Integer> userIdCalendarId = new HashMap<>();
            userUsedFreeDays.forEach(userUsedFreeDay -> userIdCalendarId.put(userUsedFreeDay.getUserId(), userUsedFreeDay.getCalendarId()));
            // Prepare string for query - transform Map<Integer, Integer> in "(x,y)
            String paramForQuery = userIdCalendarIdStringForQuery(userIdCalendarId);
            // Query for Delete user free days by unique columns (combination of user_id and calendar_id)
            String sql = "DELETE FROM `user_used_free_days` WHERE ( user_id, calendar_id ) IN (" + paramForQuery + ")";

            // Executing query
            try {
                jdbcTemplate.execute(sql);
            } catch (Exception e) {
                throw new CustomException(e.getMessage());
            }

            // Old way for deleting, which we change wit native query
//            userUsedFreeDays.forEach(userUsedFreeDay -> {
//                userUsedFreeDaysRepository.deleteByUserIdAndCalendarId(userUsedFreeDay.getUserId(), userUsedFreeDay.getCalendarId());
//            });

        }

    }

    private String userIdCalendarIdStringForQuery(Map<Integer, Integer> userIdCalendarId) {
        return userIdCalendarId.entrySet().stream().map(e -> "(" + e.getKey() + "," + e.getValue() + ")").collect(Collectors.joining(","));
    }
}

/**
 * Zoranovi primeri kako da se koristi stream() sa stringom...
 * Dole je primer moje metode, koju je Zoran "zamenio" jednom linijom koda
 * Dao nam je 2 mogucnosti ("result" i "result2" kako se moze resiti)
 */

// Map<Integer, Integer> userIdCalendarId = Map.of(1,1,2,2,3,3);
// String result = userIdCalendarId.entrySet().stream().map(e ->"(" + e.getKey() + "," + e.getValue() + ")").collect(Collectors.joining(","));
// String result2 = String.join(",", userIdCalendarId.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(e ->"(" + e.getKey() + "," + e.getValue() + ")").toList());
//
//private String userIdCalendarIdStringForQuery(Map<Integer, Integer> userIdCalendarId) {
//    StringBuilder sb = new StringBuilder();
//    userIdCalendarId.forEach((key, value) -> {
//        sb.append("(").append(key).append(",").append(value).append("),");
//    });
//
//    return sb.toString();
//}