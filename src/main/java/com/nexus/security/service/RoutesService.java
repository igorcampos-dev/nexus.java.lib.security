package com.nexus.security.service;

import com.nexus.security.model.dto.RoutesDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@AllArgsConstructor
public class RoutesService {

    private final HttpSecurity http;
    private final List<RoutesDTO> routesList;

    public SecurityFilterChain configure() throws Exception {
        http.cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                    routesList.forEach(routes -> authorize.requestMatchers(routes.method(),
                                                                           routes.route()).permitAll());
                    authorize.anyRequest().authenticated();
                });

        return http.build();
    }
}
