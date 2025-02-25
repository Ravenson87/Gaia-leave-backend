package com.caci.gaia_leave.shared_tools.helper;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidation {
    private final JwtService jwtService;
    private final AppProperties appProperties;

    public boolean validate(HttpServletRequest request, String endpoint) {
    String fullName = jwtService.getFullName(request);
    String roleId = jwtService.getRole(request);

    if(roleId == "1"){
        appProperties.setSuperAdminEnabled(true);
        appProperties.setAdminFullName(fullName);
        return true;
    }




        return false;
    }


}
