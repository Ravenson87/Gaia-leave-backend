package com.caci.gaia_leave.shared_tools.helper;

import com.caci.gaia_leave.shared_tools.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidation {
    private final JwtService jwtService;

    public boolean validate(HttpServletRequest request, String endpoint) {
    String fullName = jwtService.getFullName(request);
    System.out.println(fullName);

        return false;
    }


}
