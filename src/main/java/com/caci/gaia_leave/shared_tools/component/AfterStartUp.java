package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.administration.service.CalendarService;
import com.caci.gaia_leave.administration.service.DayOffManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AfterStartUp {
    private final CalendarService calendarService;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws ParseException {

    }

}
