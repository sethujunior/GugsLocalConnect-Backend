package cput.ac.za.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// Cross-cutting "Security" row of the architecture diagram: JWT Auth,
// input validation happens in DTOs/services, RBAC via role-based authorities.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Auth endpoints: open to anyone (that's the point)
                        .requestMatchers("/api/auth/**").permitAll()

                        // 2. Public browsing: categories, search, viewing business
                        // profiles/reviews — no login needed
                        .requestMatchers(HttpMethod.GET, "/api/categories", "/api/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/search", "/api/search/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/businesses/getAll", "/api/businesses/read/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/reviews/business/**").permitAll()

                        // 3. IMPORTANT: this must come BEFORE rule 4 below. "/me/**"
                        // is more specific than "/*/services", so it has to be
                        // checked first, or rule 4's wildcard would wrongly match
                        // "/api/businesses/me/services" too and expose it publicly.
                        .requestMatchers("/api/businesses/me/**").authenticated()

                        // 4. Public — anyone can see what services a business
                        // offers before booking, without owning that business.
                        .requestMatchers(HttpMethod.GET, "/api/businesses/*/services").permitAll()

                        // 5. Everything else (bookings, messaging, posting reviews,
                        // managing your own business) requires a valid JWT
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of(
                "http://localhost:4200",
                "http://127.0.0.1:4200",
                "http://localhost:5500",
                "http://127.0.0.1:5500"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}