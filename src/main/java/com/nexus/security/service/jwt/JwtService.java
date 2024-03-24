package com.nexus.security.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nexus.security.properties.TokenProperties;
import com.nexus.security.properties.SecurityProperties;
import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JwtService {

    private final SecurityProperties jwtProperties;

    public String encode(UserDetails userDetails) {
        try {
            return this.generateToken(userDetails);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TokenProperties decode(String token) {
        try {
            return this.decryptToken(token);
        } catch (JWTDecodeException e){
            throw new JWTDecodeException("Houve um erro ao decodificar o token, está corrompido.");
        } catch (TokenExpiredException e){
            throw new TokenExpiredException("Erro de expiração de token, está expirado.", Instant.now());
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private String generateToken(UserDetails userDetails){
        return JWT.create()
                .withIssuer("api-auth")
                .withClaim("username", userDetails.getUsername())
                .withClaim("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withExpiresAt(getExpirationDate())
                .sign(Algorithm.HMAC256(jwtProperties.getSignature()));
    }

    public TokenProperties decryptToken(String token){
        DecodedJWT tokenDecoded = JWT.require(Algorithm.HMAC256(jwtProperties.getSignature()))
                .withIssuer("api-auth")
                .build()
                .verify(token);

        return TokenProperties.builder()
                .username(tokenDecoded.getClaim("username").asString())
                .authorities(tokenDecoded.getClaim("authorities").asList(String.class))
                .build();
    }

    private Instant getExpirationDate(){
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }

}
