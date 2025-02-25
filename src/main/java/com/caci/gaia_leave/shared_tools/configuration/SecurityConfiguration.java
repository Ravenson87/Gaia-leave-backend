package com.caci.gaia_leave.shared_tools.configuration;

import com.caci.gaia_leave.shared_tools.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AppProperties appProperties;

    /**
     * Configures the security filter chain.
     *
     * @param http HttpSecurity instance used to configure security settings.
     * @return SecurityFilterChain configured with authentication, session, and CORS policies.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Define request matchers for endpoints requiring authentication
        // AntPathRequestMatcher deo spring security klase koja se koristi za definisanje putanje koje treba da bude
        // pokrivena sigurnosnim pravilima
        RequestMatcher[] actuatorEndpoints = Arrays.stream(appProperties.getActuatorAuthorisationEndpoints())
                .map(AntPathRequestMatcher::new)
                .toArray(RequestMatcher[]::new);

        RequestMatcher[] basicAuthEndpoints = Arrays.stream(appProperties.getExcludedRoutes())
                .map(AntPathRequestMatcher::new)
                .toArray(RequestMatcher[]::new);

        try {
            http
                    .authorizeHttpRequests(authorizeRequests ->
                            authorizeRequests
                                    // Secure actuator and basic authentication endpoints
                                    .requestMatchers(actuatorEndpoints).authenticated()
                                    .requestMatchers(basicAuthEndpoints).authenticated()
                                    // Allow all other endpoints without authentication
                                    .requestMatchers(antMatcher("/**")).permitAll())
                    // Set session management to stateless (no session persistence)
                    .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    // Disable CSRF protection (not needed for stateless APIs)
                    .csrf(AbstractHttpConfigurer::disable)
                    // Enable Cross-Origin Resource Sharing (CORS) with default settings
                    .cors(withDefaults())
                    // Enable HTTP Basic authentication
                    .httpBasic(withDefaults());
            return http.build();
        } catch (Exception e) {
            // Wrap security configuration errors in a custom exception
            throw new CustomException(e.getMessage());
        }
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) to allow controlled access from external domains.
     *
     * @return CorsConfigurationSource containing CORS settings.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow requests from any origin
        config.setAllowedOrigins(Collections.singletonList("*"));
        // Allow only specific HTTP methods defined in application properties
        config.setAllowedMethods(Arrays.asList(appProperties.getAllowedMethods()));
        // Allow specific headers defined in application properties
        config.setAllowedHeaders(Arrays.asList(appProperties.getSecurityHeaders()));
        // Expose the "x-auth-token" header to clients
        config.setExposedHeaders(Collections.singletonList("x-auth-token"));

        // Apply CORS configuration to all API endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }


}
