package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.OvertimeHours;
import com.caci.gaia_leave.administration.model.response.OvertimeHoursResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OvertimeHoursRepository extends CrudRepository<OvertimeHours, Integer> {
    boolean existsByUserIdAndCalendarId(Integer userId, Integer calendarId);
    List<OvertimeHours> findAllByUserId(Integer userId);
    boolean existsByUserId(Integer userId);
    Optional<OvertimeHours> findByUserIdAndCalendarId(Integer userId, Integer calendarId);

    @Query(
            name = "OvertimeHours.overtimeHoursByUserAndDate",
            nativeQuery = true
    )
    Integer sumOvertimeWorkingHours(@Param("user_id") Integer userId, @Param("start_date") String startDate, @Param("end_date") String endDate);

}
