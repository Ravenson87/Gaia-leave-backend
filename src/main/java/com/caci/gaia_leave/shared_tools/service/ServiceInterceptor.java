package com.caci.gaia_leave.shared_tools.service;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.component.UserValidation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ServiceInterceptor implements HandlerInterceptor {

    private final MappingService mappingService;
    private final UserValidation userValidation;
    private final AppProperties appProperties;


    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        String endpoint = mappingService.endpoint(request);
        // Exclude basic auth endpoints
        if (!endpoint.equals("/**")
                && !Arrays.asList(appProperties.getExcludedRoutes()).contains(endpoint)) {
            return userValidation.validate(request, endpoint);
        }
        return false;
    }


}
