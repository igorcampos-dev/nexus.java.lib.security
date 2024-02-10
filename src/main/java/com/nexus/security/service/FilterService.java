package com.nexus.security.service;

import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Component
@AllArgsConstructor
@ConditionalOnProperty(name = "enable.security.routes", havingValue = "true")
public class FilterService extends OncePerRequestFilter {

    public static final Logger LOG = Logger.getLogger(FilterService.class.getName());

    private final SecurityContextInjector contextInjector;

    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable FilterChain filterChain){
        try {

            assert request != null;
            assert response != null;
            assert filterChain != null;

            LOG.info(String.format("have received the data: response: %s, request: %s and filterchain: %s", response, request, filterChain));

            recoverToken(Objects.requireNonNull(request))
                    .ifPresent(contextInjector::injectContext);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
           contextInjector.exception(e, request, response);
        }
    }

    private Optional<String> recoverToken(HttpServletRequest request){
        LOG.info(String.format("return of recover token is: %s", Optional.ofNullable(request.getHeader("Authorization")).map(authHeader -> authHeader.replace("Bearer", "").strip())));
        return Optional.ofNullable(request.getHeader("Authorization"))
                .map(authHeader -> authHeader.replace("Bearer", "").strip());
    }
}
