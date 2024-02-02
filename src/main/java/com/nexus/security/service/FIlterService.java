package com.nexus.security.service;

import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class FIlterService extends OncePerRequestFilter {

    private final Filter filter;

    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable FilterChain filterChain){
        try {
            assert request != null;
            assert response != null;
            assert filterChain != null;

            recoverToken(Objects.requireNonNull(request))
                    .ifPresent(filter::injectContext);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            filter.exception(e, request, response);
        }
    }


    private Optional<String> recoverToken(HttpServletRequest request){
        return Optional.ofNullable(request.getHeader("Authorization"))
                .map( authHeader -> authHeader.replace("Bearer", "").strip());
    }
}
