package com.blazek.events.config;

import com.blazek.events.filters.UserProvisioningFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the {@link HttpSecurity} to configure
     * @param userProvisioningFilter the custom filter for provisioning users based on JWT data
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if a configuration error occurs
     */

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            UserProvisioningFilter userProvisioningFilter
    ) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        // Catch all rule
                        authorize.anyRequest().authenticated())
                // Disable CSRF (not needed for stateless REST APIs)
                //Spring has CSRF protection enabled by default
                .csrf(csrf -> csrf.disable())
                // Do not create or use HTTP sessions; rely entirely on JWT tokens
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Enable JWT-based authentication using OAuth2 Resource Server
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                // Register the custom user provisioning filter after JWT authentication is complete
                .addFilterAfter(userProvisioningFilter, BearerTokenAuthenticationFilter.class);

        return http.build();
    }
}
