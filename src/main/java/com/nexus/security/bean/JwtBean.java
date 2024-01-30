package com.nexus.security.bean;

import com.nexus.security.properties.JwtProperties;
import com.nexus.security.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtBean {

    private final JwtProperties jwtProperties;

    @Bean
    public JwtService jwtService(){
        return new JwtService(jwtProperties);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}