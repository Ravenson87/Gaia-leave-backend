package com.caci.gaia_leave.shared_tools.configuration;

import com.caci.gaia_leave.shared_tools.service.ServiceInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class ServiceInterceptorAppConfig implements WebMvcConfigurer {
    private final AppProperties appProperties;
    private final ServiceInterceptor serviceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(serviceInterceptor).
                excludePathPatterns(appProperties.getExcludedRoutes());
    }


}
