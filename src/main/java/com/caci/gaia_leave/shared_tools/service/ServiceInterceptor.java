package com.caci.gaia_leave.shared_tools.service;

import com.caci.gaia_leave.shared_tools.configuration.AppProperties;
import com.caci.gaia_leave.shared_tools.helper.UserValidation;
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

    //TODO
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//    String endpoint = mappingService.endpoints(request);
////    userValidation.validate(request, endpoint);
//
////    if(endpoint.equals("/**")) {
////
////    }
//
//        return false;
//    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        System.out.println("request: " + request);
        String endpoint = mappingService.endpoint(request);
        System.out.println("endpoint: " + endpoint);
        // Exclude basic auth endpoints
        if (!endpoint.equals("/**")
                && !Arrays.asList(appProperties.getExcludedRoutes()).contains(endpoint)) {
            System.out.println("Interceptor u if-u!!!");
            return userValidation.validate(request, endpoint);
        }
        return false;
    }


}
