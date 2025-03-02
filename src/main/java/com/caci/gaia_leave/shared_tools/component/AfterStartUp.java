package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.shared_tools.model.WorikingDayType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static com.caci.gaia_leave.shared_tools.helper.AllHelpers.*;

@Component
@RequiredArgsConstructor
public class AfterStartUp {

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {


//            int year = 2025;
//            List<String> weekends = getWeekends(year, "dd-MM-yyyy");
//            weekends.forEach(System.out::println);


    }

}
