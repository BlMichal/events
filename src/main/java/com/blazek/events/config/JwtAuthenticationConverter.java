package com.blazek.events.config;


import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {

    /**
     * Converts a {@link Jwt} token into a {@link JwtAuthenticationToken},
     * extracting the granted authorities (roles) from the token.
     * <p>
     * This method is typically used in custom implementations of
     * {@link org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter}
     * to customize how roles or authorities are extracted from the JWT.
     * </p>
     *
     * @param source the incoming {@link Jwt} token
     * @return a {@link JwtAuthenticationToken} containing the JWT and its associated authorities
     */
    @Override
    public JwtAuthenticationToken convert(@NotNull Jwt source) {
        Collection<GrantedAuthority> grantedAuthorities = extractAuthorities(source);
        return new JwtAuthenticationToken(source, grantedAuthorities);
    }

    /**
     * Extracts a collection of {@link GrantedAuthority} objects from a JWT token.
     * <p>
     * This method specifically looks for a "realm_access" claim in the JWT,
     * which should contain a "roles" field (typically a list of strings).
     * It then maps each valid string role to a {@link SimpleGrantedAuthority}
     * with the prefix "role_" (note: lowercase).
     * </p>
     *
     * <p>
     * If the claim is missing, null, or not in the expected format,
     * the method returns an empty list.
     * </p>
     *
     * @param jwt the JWT token containing role claims (e.g. from Keycloak or other OIDC providers)
     * @return a collection of {@link GrantedAuthority} based on the roles in the JWT;
     *         or an empty collection if no valid roles are found
     */
    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt){

        // Extract "realm_access" claim from the token
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        if(realmAccess == null || !realmAccess.containsKey("roles")){
            return Collections.emptyList();
        }

        // Extract the roles list (type-check)
        Object rolesObject = realmAccess.get("roles");

        if (!(rolesObject instanceof List<?> rolesList)) {
            return Collections.emptyList();
        }

        // Map each role string to a GrantedAuthority
        return rolesList.stream()
                .filter(role -> role instanceof String)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}
