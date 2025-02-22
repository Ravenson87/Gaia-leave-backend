package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.administration.model.response.UserResponse;
import com.caci.gaia_leave.administration.repository.response.UserResponseRepository;
import com.caci.gaia_leave.administration.service.AuthorizationService;
import com.caci.gaia_leave.administration.service.RoleService;
import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AfterStartUp {


    private final JwtService jwtService;
    private final AuthorizationService authorizationService;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        authorizationService.login("system", "3011972Fenix!");
    }
}
