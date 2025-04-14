package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.administration.service.CalendarService;
import com.caci.gaia_leave.administration.service.EndpointService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final EndpointService endpointService;
    private final CalendarService calendarService;

    /**
     * Populate endpoint table.
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 30000) // 5 min
    public void doPopulateEndpoints() {
        endpointService.populate();
    }

    @Scheduled(cron = "0 0 0 1 1 *")
    public void populateCalendar() { calendarService.populateCalendar(); }
}
