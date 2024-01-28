package com.nexus.security.service;

import com.nexus.security.model.dto.TokenPropertiesDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String encode(UserDetails userDetails);
    TokenPropertiesDTO decode(String token);
}
