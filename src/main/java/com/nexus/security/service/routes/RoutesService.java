package com.nexus.security.service.routes;

import com.nexus.security.dto.Routes;
import com.nexus.security.service.filter.FilterService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Builder
@AllArgsConstructor
public class RoutesService {

    private final HttpSecurity http;
    private final List<Routes> permitAllRoutes;
    private final List<Routes> permitHasRoleAdmin;
    private final List<Routes> getPermitHasRoleUser;
    private final FilterService filterService;

    public SecurityFilterChain configure() throws Exception {
        http.cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {

                    permitAllRoutes.forEach(routes -> authorize.requestMatchers(routes.method(),
                                                                                routes.route()).permitAll());

                    permitHasRoleAdmin.forEach(routes -> authorize.requestMatchers(routes.method(),
                                                                                   routes.route()).hasRole("ADMIN"));

                    getPermitHasRoleUser.forEach(routes -> authorize.requestMatchers(routes.method(),
                                                                                     routes.route()).hasRole("USER"));

                    authorize.anyRequest().authenticated();
                }).addFilterBefore(filterService, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
