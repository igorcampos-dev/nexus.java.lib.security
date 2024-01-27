package com.nexus.security.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@AllArgsConstructor
public class JwtProperties {
    private String signature;
}
