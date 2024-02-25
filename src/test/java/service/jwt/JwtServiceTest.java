package service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.nexus.security.properties.TokenProperties;
import com.nexus.security.properties.SecurityProperties;
import com.nexus.security.service.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    @Mock
    private SecurityProperties jwtProperties;

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtProperties.getSignature()).thenReturn("testSignature");
    }

    @Test
    void testEncodeAndDecodeValidToken() {
        UserDetails userDetails = mockUserDetails();
        String token = jwtService.encode(userDetails);
        assertNotNull(token);
        TokenProperties decodedToken = jwtService.decode(token);
        assertNotNull(decodedToken);
        assertEquals(userDetails.getUsername(), decodedToken.username());
        assertEquals(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(), decodedToken.authorities());
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
        assertThrows(RuntimeException.class, () -> jwtService.encode(mockUserDetails()));
    }

    private UserDetails mockUserDetails() {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(() -> "ROLE_USER", () -> "ROLE_ADMIN");
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return "user@example.com";
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }

        };
    }

    private String generateExpiredToken() {
        Instant expirationDate = LocalDateTime.now().minusSeconds(2).toInstant(ZoneOffset.of("-03:00"));
        Algorithm algorithm = Algorithm.HMAC256("testSignature");

        return JWT.create()
                .withClaim("email", "user@example.com")
                .withIssuer("api-auth")
                .withExpiresAt(expirationDate)
                .sign(algorithm);
    }
}
