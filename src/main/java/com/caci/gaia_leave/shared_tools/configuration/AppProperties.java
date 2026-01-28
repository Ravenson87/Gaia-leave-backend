package com.caci.gaia_leave.shared_tools.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Configuration
public class AppProperties {

    private String adminFullName = null;
    private boolean superAdminEnabled = false;
    private Integer userId = null;

    private final String[] excludedPackets = {"tools", "ui", "api"};

    private final String[] actuatorAuthorisationEndpoints = {
            "/actuator/auditevents",
            "/actuator/beans",
            "/actuator/caches",
            "/actuator/conditions",
            "/actuator/configprops",
            "/actuator/env",
            "/actuator/flyway",
            "/actuator/health",
            "/actuator/httptrace",
            "/actuator/info",
            "/actuator/integrationgraph",
            "/actuator/loggers",
            "/actuator/liquibase",
            "/actuator/metrics",
            "/actuator/mappings",
            "/actuator/scheduledtasks",
            "/actuator/sessions"
    };

    private final String[] basicAuthorization = {};
    private final String[] allowedMethods = {"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"};
    private final String[] securityHeaders = {"authorization", "content-type", "x-auth-token", "permissions"};

    private final String[] excludedRoutes = {
            "/api/v1/auth/**",
            "/api/v1/sse/**",
            "/api/v1/user/read-by-hash/**",
            "/error"
    };

    @Value("${spring.application.name}")
    private String msApplicationName;

    @Value("${spring.security.user.name}")
    private String globalUser;

    @Value("${spring.security.user.password}")
    private String globalPassword;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${upload.image.path}")
    private String uploadImagePath;

    @Value("${domain.name}")
    private String domainName;

    @Value("${img.storage.gaia.leave}")
    private String imageStorageGaiaLeave;

    @Value("${spring.mail.send.from}")
    private String sendMailFrom;
}
