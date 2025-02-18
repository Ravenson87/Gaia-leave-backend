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

    /**
     * Excluded packets from mapping.
     */
    public final String[] excludedPackets = {"tools", "ui", "api"};

    @Value("${spring.application.name}")
    private String msApplicationName;

    @Value("${spring.security.user.name}")
    private String globalUser;

    @Value("${spring.security.user.password}")
    private String globalPassword;

}
