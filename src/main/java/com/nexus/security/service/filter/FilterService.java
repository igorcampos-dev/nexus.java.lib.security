package com.nexus.security.service.filter;

import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Objects;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class FilterService extends OncePerRequestFilter {

    public static final Logger LOG = Logger.getLogger(FilterService.class.getName());
    private final SecurityContextInjector contextInjector;
    @Override
    protected void doFilterInternal(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable FilterChain filterChain){
        FilterSupport filterSupport = new FilterSupport();
        try {

            assert request != null;
            assert response != null;
            assert filterChain != null;

            LOG.info(String.format("have received the data: response: %s, request: %s and filterchain: %s", response, request, filterChain));

            filterSupport.recoverToken(Objects.requireNonNull(request))
                    .ifPresent(contextInjector::injectContext);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
           filterSupport.exception(e, request, response);
        }
    }
}
