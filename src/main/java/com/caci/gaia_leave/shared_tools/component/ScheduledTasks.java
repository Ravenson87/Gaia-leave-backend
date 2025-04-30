package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.administration.model.request.User;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import com.caci.gaia_leave.administration.service.CalendarService;
import com.caci.gaia_leave.administration.service.EndpointService;
import com.caci.gaia_leave.shared_tools.service.ReligiousHolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.listConverter;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final EndpointService endpointService;
    private final CalendarService calendarService;
    private final UserRepository userRepository;
    private final ReligiousHolidayService religiousHolidayService;
    /**
     * Populate endpoint table.
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 30000) // 5 min
    public void doPopulateEndpoints() {
        endpointService.populate();
    }

    /**
     * Populate calendar and check all personal/religious holidays for every active user
     */
    @Scheduled(cron = "0 0 0 1 1 *")
    public void populateCalendar() {
        calendarService.populateCalendar();

        List<User> users = listConverter(userRepository.findAll());
        List<Integer> userIds = users.stream().map(User::getId).toList();

        userIds.forEach(religiousHolidayService::setReligiousHoliday);
    }
}
