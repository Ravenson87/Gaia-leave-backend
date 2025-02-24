package com.caci.gaia_leave.shared_tools.configuration;

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //AntPathRequestMatcher deo spring security klase koja se koristi za definisanje putanje koje treba da bude pokrivena sigurnosnim pravilima
        RequestMatcher[] actuatorEndpoints = Arrays.stream(appProperties.getActuatorAuthorisationEndpoints())
                .map(AntPathRequestMatcher::new)
                .toArray(RequestMatcher[]::new);

        RequestMatcher[] basicAuthEndpoints = Arrays.stream(appProperties.getExcludedRoutes())
                .map(AntPathRequestMatcher::new)
                .toArray(RequestMatcher[]::new);

        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(actuatorEndpoints).authenticated()
                                .requestMatchers(basicAuthEndpoints).authenticated()
                                .requestMatchers(antMatcher("/**")).permitAll())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .httpBasic(withDefaults());


        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedMethods(Arrays.asList(appProperties.getAllowedMethods()));
        config.setAllowedHeaders(Arrays.asList(appProperties.getAllowedMethods()));
        config.setExposedHeaders(Collections.singletonList("x-auth-token"));


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}
