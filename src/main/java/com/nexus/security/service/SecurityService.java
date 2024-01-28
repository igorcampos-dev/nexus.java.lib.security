package com.nexus.security.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityService {

    private final HttpSecurity httpSecurity;

    public static SecurityFilterChain configureSecurity(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.build();
    }
}
