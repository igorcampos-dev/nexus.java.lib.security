package service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.nexus.security.model.dto.TokenPropertiesDTO;
import com.nexus.security.properties.JwtProperties;
import com.nexus.security.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtServiceTest {

    @Mock
    private JwtProperties jwtProperties;

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtProperties.getSignature()).thenReturn("testSignature");
    }

    @Test
    void testEncodeAndDecodeValidToken() {
        String email = "user@example.com";
        String role = "ROLE_USER";
        String token = jwtService.encode(email, role);
        assertNotNull(token);
        TokenPropertiesDTO decodedToken = jwtService.decode(token);
        assertEquals(email, decodedToken.email());
        assertEquals(role, decodedToken.role());
    }

    @Test
    void testDecodeCorruptedToken() {
        String corruptedToken = "corruptedToken";
        assertThrows(JWTDecodeException.class, () -> jwtService.decode(corruptedToken));
    }

    @Test
    void testDecodeExpiredToken() {
        String expiredToken = generateExpiredToken();
        assertThrows(TokenExpiredException.class, () -> jwtService.decode(expiredToken));
    }

    @Test
    void testEncodeWithException() {
        when(jwtProperties.getSignature()).thenReturn(null);
        assertThrows(RuntimeException.class, () -> jwtService.encode("user@example.com", "ROLE_USER"));
    }

    private String generateExpiredToken() {
        Instant expirationDate = LocalDateTime.now().minusSeconds(2).toInstant(ZoneOffset.of("-03:00"));
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSignature());

        return JWT.create()
                .withClaim("email", "user@example.com")
                .withClaim("role", "ROLE_USER")
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }
}
