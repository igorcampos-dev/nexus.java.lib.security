package com.nexus.security.service;

import com.nexus.security.model.dto.RotaMetodoDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@AllArgsConstructor
public class RoutesService {
        private HttpSecurity http;
        private List<RotaMetodoDTO> rotasMetodos;

        public SecurityFilterChain configure() throws Exception {
                http.cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(authorize -> {
                        for (RotaMetodoDTO rotaMetodoDTO : rotasMetodos) {
                            authorize.requestMatchers(rotaMetodoDTO.getMetodo(),
                                                      rotaMetodoDTO.getRota()).permitAll();
                        }
                        authorize.anyRequest().authenticated();
                    });

            return http.build();
        }

}
