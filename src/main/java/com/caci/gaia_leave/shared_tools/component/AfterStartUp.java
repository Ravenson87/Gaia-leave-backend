package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.administration.service.RoleService;
import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AfterStartUp {

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {

    }
}
