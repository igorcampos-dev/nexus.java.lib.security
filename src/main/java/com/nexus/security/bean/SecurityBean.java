package com.nexus.security.bean;

import com.nexus.security.properties.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class SecurityBean {
    private static final int BCRYPT_STRENGTH = 14;


    @Bean
    public SecurityProperties securityProperties(){
        return new SecurityProperties();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_STRENGTH, new SecureRandom());
    }
}
