package com.cine.ms_cliente.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Extraer el token del encabezado Authorization[cite: 8]
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Si no hay encabezado o no empieza con "Bearer ", deja pasar la petición (Spring Security la bloqueará después si es privada)[cite: 8]
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraer el JWT (quitando la palabra "Bearer ")[cite: 8]
        jwt = authHeader.substring(7);
        try {
            username = jwtService.extractUsername(jwt);
            
            // 3. Si hay usuario y no está autenticado aún en este contexto
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // Si el token es válido, creamos el objeto de autenticación y lo guardamos en el contexto[cite: 8]
                if (jwtService.isTokenValid(jwt, username)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            new ArrayList<>() // Aquí irían los roles
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken); // Cargamos la identidad en el contexto[cite: 8]
                }
            }
        } catch (Exception e) {
            // Si el token es inválido o expiró, el filtro simplemente falla limpiamente
        }
        
        filterChain.doFilter(request, response);
    }
}