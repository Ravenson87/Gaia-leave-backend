package com.caci.gaia_leave.shared_tools.component;

import com.caci.gaia_leave.administration.model.response.RoleResponse;
import com.caci.gaia_leave.administration.repository.response.RoleResponseRepository;
import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserValidation {
    private final JwtService jwtService;
    private final AppProperties appProperties;
    private final RoleResponseRepository roleResponseRepository;


    public boolean validate(HttpServletRequest request, String endpoint) {
    String fullName = jwtService.getFullName(request);

    if(jwtService.superAdmin(request)){
        appProperties.setSuperAdminEnabled(true);
        appProperties.setAdminFullName(fullName);
        return true;
    }
    Integer roleId = jwtService.getRoleId(request);
    Optional<RoleResponse> roleResponse = roleResponseRepository.findById(roleId);
    if(roleResponse.isEmpty()){
        return false;
    }

    Set<String> allowedEndpoints = roleResponse.get().getRoleEndpoints().stream()
            .map(roleEndpoints -> roleEndpoints.getEndpoint().getEndpoint())
            .collect(Collectors.toSet());

    if(allowedEndpoints.contains(endpoint)){
        appProperties.setSuperAdminEnabled(false);
        appProperties.setAdminFullName(fullName);
        return true;
    }

        return false;
    }

}
