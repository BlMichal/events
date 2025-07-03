package com.blazek.events.filters;

import com.blazek.events.domain.entities.User;
import com.blazek.events.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * It acts as a bridge between an external identity provider and your own database.
 */
@Component
@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Get authentication from Spring Security context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // We verify that the user logged in and principal is JWT token
        if(auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof Jwt jwt) {
            // Get subject from JWT token (Keycloak ID)
            UUID keycloakId =  UUID.fromString(jwt.getSubject());
            // Check if user already exists in the DB, if not, we create new user
           if(!userRepository.existsById(keycloakId)){

               User user = new User();
               user.setId(keycloakId);
               user.setName(jwt.getClaims().get("preferred_username").toString());
               user.setEmail(jwt.getClaims().get("email").toString());

               userRepository.save(user);
           }
        }

        filterChain.doFilter(request,response);
    }
}
