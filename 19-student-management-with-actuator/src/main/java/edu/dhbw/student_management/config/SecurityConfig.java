package edu.dhbw.student_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(Customizer.withDefaults());

        http.authorizeHttpRequests(auth -> auth
                // Swagger / OpenAPI and h2 console
                .requestMatchers("/api/docs/**", "/api/docs-json/**", "/swagger-ui/**", "/v3/api-docs/**",
                        "/h2-console/**")
                .permitAll()

                // Make only courses publicly accessible via GET
                .requestMatchers(HttpMethod.GET, "/api/courses**")
                .permitAll()

                // Actuator endpoints accessible only to users with ADMIN role
                .requestMatchers("/actuator/**").hasRole("ADMIN")

                // All other endpoints are authenticated
                .anyRequest().authenticated());

        http.httpBasic(Customizer.withDefaults());

        // allow frame for H2 console
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
