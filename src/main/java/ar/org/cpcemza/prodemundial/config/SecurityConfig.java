package ar.org.cpcemza.prodemundial.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final RateLimitFilter        rateLimitFilter;

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                // CORS debe procesarse ANTES que cualquier regla de seguridad
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .sessionManagement(s -> s
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(3)
                        .expiredUrl("/api/auth/expirada")
                )

                .headers(h -> h
                        .frameOptions(f -> f.deny())
                        .contentTypeOptions(c -> {})
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .maxAgeInSeconds(31_536_000)
                        )
                        .referrerPolicy(r -> r
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                        )
                        .permissionsPolicy(p -> p
                                .policy("camera=(), microphone=(), geolocation=(), payment=()")
                        )
                )

                .authorizeHttpRequests(auth -> auth
                        // Preflight OPTIONS — SIEMPRE permitir antes que todo
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Rutas públicas
                        .requestMatchers(
                                "/health",
                                "/api/auth/login",
                                "/api/auth/logout",
                                "/api/auth/me",
                                "/api/auth/expirada"
                        ).permitAll()
                        // Solo admin
                        .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                        // Todo lo demás requiere sesión activa
                        .anyRequest().authenticated()
                )

                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .deleteCookies("PRODE_SESSION")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
                )

                .authenticationProvider(authenticationProvider())
                .addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Orígenes permitidos desde variable de entorno
        // En desarrollo local también podés agregar http://localhost:5500, etc.
        config.setAllowedOrigins(List.of(allowedOrigins.split(",")));

        // Todos los métodos incluyendo OPTIONS para preflights
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Headers que envía el frontend
        config.setAllowedHeaders(List.of(
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
        ));

        // Headers que el frontend puede leer en la respuesta
        config.setExposedHeaders(List.of("Set-Cookie"));

        // CRÍTICO con sesiones — el navegador envía la cookie
        config.setAllowCredentials(true);

        // Cache del preflight por 1 hora
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // /** en vez de /api/**
        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(userDetailsService);
        p.setPasswordEncoder(passwordEncoder());
        p.setHideUserNotFoundExceptions(true);
        return p;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // Forzamos explícitamente a usar tu proveedor conectado a Neon
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());

        return authenticationManagerBuilder.build();
    }
}
