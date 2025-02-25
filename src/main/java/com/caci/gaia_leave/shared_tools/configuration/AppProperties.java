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

    /**
     * Admin user.
     */
    private String adminFullName = null;

    private boolean superAdminEnabled = false;

    /**
     * Excluded packets from mapping.
     */
    private final String[] excludedPackets = {"tools", "ui", "api"};

    private final String[] actuatorAuthorisationEndpoints =
            {
                    "/actuator/auditevents",        // Exposes audit events information for the current application
                    "/actuator/beans",              // Displays a complete list of all the Spring beans in your application
                    "/actuator/caches",             // Exposes available caches
                    "/actuator/conditions",         // Shows the conditions that were evaluated on configuration and auto-configuration classes
                    "/actuator/configprops",        // Displays a collated list of all @ConfigurationProperties
                    "/actuator/env",                // Exposes properties from Spring’s ConfigurableEnvironment
                    "/actuator/flyway",             // Shows any Flyway database migrations that have been applied
                    "/actuator/health",             // Shows application health information
                    "/actuator/httptrace",          // Displays HTTP trace information (last 100 request-response exchanges)
                    "/actuator/info",               // Displays arbitrary application info
                    "/actuator/integrationgraph",   // Shows the Spring Integration graph
                    "/actuator/loggers",            // Shows and modifies the configuration of loggers
                    "/actuator/liquibase",          // Shows any Liquibase database migrations that have been applied
                    "/actuator/metrics",            // Shows ‘metrics’ information for the current application
                    "/actuator/mappings",           // Displays a collated list of all @RequestMapping paths
                    "/actuator/scheduledtasks",     // Displays the scheduled tasks in your application
                    "/actuator/sessions"            // Allows retrieval and deletion of user sessions
            };
    private final String[] basicAuthorization = {};

    private final String[] allowedMethods = {"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"};

    private final String[] securityHeaders = {"authorization", "content-type", "x-auth-token", "permissions"};

    private final String[] excludedRoutes = {
            "/api/v1/auth/**"
    };

    @Value("${spring.application.name}")
    private String msApplicationName;

    @Value("${spring.security.user.name}")
    private String globalUser;

    @Value("${spring.security.user.password}")
    private String globalPassword;

    @Value("${jwt.secret}")
    private String jwtSecret;

}
