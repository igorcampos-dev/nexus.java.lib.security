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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
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

        String token = jwtService.encode(userDetails);
        assertNotNull(token);
        TokenPropertiesDTO decodedToken = jwtService.decode(token);
        assertNotNull(decodedToken);
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
                return null;
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
