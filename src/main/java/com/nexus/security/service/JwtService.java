package com.nexus.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nexus.security.model.dto.TokenPropertiesDTO;
import com.nexus.security.properties.JwtProperties;
import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public String encode(UserDetails userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSignature());
            return JWT.create()
                    .withIssuer("api-auth")
                    .withClaim("email", userDetails.getUsername())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TokenPropertiesDTO decode(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSignature());
            DecodedJWT tokenDecoded = JWT.require(algorithm)
                    .withIssuer("api-auth")
                    .build()
                    .verify(token);

            String email = tokenDecoded.getClaim("email").asString();

            return new TokenPropertiesDTO(email);

        } catch (JWTDecodeException e){
            throw new JWTDecodeException("Houve um erro ao decodificar o token, está corrompido.");
        } catch (TokenExpiredException e){
            throw new TokenExpiredException("Erro de expiração de token, está expirado.", Instant.now());
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private Instant getExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
