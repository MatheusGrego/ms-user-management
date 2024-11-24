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
                    // Open authentication routes for everyone
                    rQ.requestMatchers("/api/auth/**").permitAll();
                    // Restrict POST on users
                    rQ.requestMatchers(HttpMethod.POST, "/api/users/**").denyAll();
                    // Managers can view users and their own profiles
                    rQ.requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("MANAGER");
                    rQ.requestMatchers(HttpMethod.GET, "/api/users/{id}").authenticated(); // Allow authenticated users to access their own profile
                    // Checklists access control
                    rQ.requestMatchers("/api/checklists/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                    // QrCodes access control
                    rQ.requestMatchers(HttpMethod.POST, "/api/qrcodes/**").hasAnyRole("MANAGER", "ADMIN");
                    rQ.requestMatchers(HttpMethod.GET, "/api/qrcodes/**").hasAnyRole("USER", "MANAGER", "ADMIN");
                    // Checklist Items access control
                    rQ.requestMatchers("/api/checklists/checklist-items/**").hasAnyRole("USER", "MANAGER", "ADMIN");

                    // All other requests require admin
                    rQ.anyRequest().hasRole("ADMIN");
                })
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
