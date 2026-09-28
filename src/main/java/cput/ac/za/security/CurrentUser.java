package cput.ac.za.security;

import org.springframework.security.core.Authentication;

// JwtAuthFilter stores the authenticated user's ID as the Authentication's
// "name" (see UsernamePasswordAuthenticationToken in JwtAuthFilter).
// Controllers call this instead of re-parsing that logic everywhere.
public class CurrentUser {
    public static Long id(Authentication authentication) {
        if (authentication == null) return null;
        return Long.valueOf(authentication.getName());
    }
}
