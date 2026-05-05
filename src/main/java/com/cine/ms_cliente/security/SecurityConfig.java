package com.cine.ms_cliente.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Deshabilitamos CSRF porque usaremos tokens, no cookies[cite: 8]
            .authorizeHttpRequests(auth -> auth
                // IMPORTANTE: Por ahora dejamos la ruta de clientes pública para que puedas probar el CRUD.
                // En el futuro, podrías cambiar esto a .authenticated() para bloquearla.
                .requestMatchers("/api/v1/clientes/**").permitAll() 
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                // Obligamos a Spring a usar política STATELESS (sin sesiones)[cite: 8]
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
            )
            // Añadimos nuestro filtro personalizado ANTES del filtro por defecto de Spring[cite: 8]
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}