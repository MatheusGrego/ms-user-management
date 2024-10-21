package com.payment.usermanagement.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(rQ -> {
                    rQ.requestMatchers("/api/auth/**").permitAll();
                            rQ.requestMatchers(HttpMethod.POST, "/api/users").denyAll();
                            rQ.requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("MANAGER");
                            rQ.requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAnyRole("USER", "MANAGER", "ADMIN");
                            rQ.requestMatchers(HttpMethod.GET, "/api/checklists/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                            rQ.requestMatchers(HttpMethod.PUT, "/api/checklists/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                            rQ.requestMatchers(HttpMethod.DELETE, "/api/checklists/**").hasRole("MANAGER");
                           rQ.requestMatchers(HttpMethod.POST, "/api/qrcodes").hasAnyRole("MANAGER", "ADMIN");
                            rQ.requestMatchers(HttpMethod.GET, "/api/qrcodes/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                            rQ.requestMatchers(HttpMethod.PUT, "/api/qrcodes/**").hasAnyRole("MANAGER", "ADMIN");
                            rQ.requestMatchers(HttpMethod.DELETE, "/api/qrcodes/**").hasAnyRole("MANAGER", "ADMIN");
                            rQ.requestMatchers(HttpMethod.POST, "/api/checklists/checklist-items/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                            rQ.requestMatchers(HttpMethod.GET, "/api/checklists/checklist-items/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                            rQ.requestMatchers(HttpMethod.PUT, "/api/checklists/checklist-items/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                            rQ.requestMatchers(HttpMethod.DELETE, "/api/checklists/checklist-items/**").hasRole("MANAGER");
                            rQ.anyRequest().hasRole("ADMIN");
                })
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
