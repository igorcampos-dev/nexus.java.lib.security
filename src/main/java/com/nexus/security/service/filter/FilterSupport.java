package com.nexus.security.service.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nexus.security.dto.Error;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class FilterSupport {

    public static final Logger LOG = Logger.getLogger(FilterService.class.getName());

    protected Optional<String> recoverToken(HttpServletRequest request){
        return Optional.ofNullable(request.getHeader("Authorization"))
                .map(authHeader -> authHeader.replace("Bearer", "").strip());
    }

    @SneakyThrows(IOException.class)
    protected void exception(Exception e, HttpServletRequest request, HttpServletResponse httpServletResponse){
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);


        PrintWriter writer = httpServletResponse.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.writeValue(writer, createError(e, request));
    }

    public Error createError(Exception e, HttpServletRequest request) {
        return Error.builder()
                .timestamp(Instant.now().toString())
                .message(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(request.getRequestURI())
                .build();

    }
}
