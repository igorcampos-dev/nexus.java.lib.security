package com.nexus.security.bean;

import com.nexus.security.properties.JwtProperties;
import com.nexus.security.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@AllArgsConstructor
public class JwtBean {
    private static final int BCRYPT_STRENGTH = 14;




    @Bean
    public JwtProperties jwtProperties(){
        return new JwtProperties();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_STRENGTH, new SecureRandom());
    }
}
