package com.caci.gaia_leave.shared_tools.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(
        dateTimeProviderRef = "auditingDateTimeProvider",
        auditorAwareRef = "auditorProvider")
@RequiredArgsConstructor
public class JpaAuditingConfig {
    private final AppProperties appProperties;

    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }

    @Bean(name = "auditorProvider")
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("caci");
    }
}
