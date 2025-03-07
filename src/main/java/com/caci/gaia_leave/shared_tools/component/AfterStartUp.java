package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.administration.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class AfterStartUp {
    private final CalendarService calendarService;
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws ParseException {
//        calendarService.populateCalendar();
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse("12-01-2018");
        System.out.println(date);
    }

}
