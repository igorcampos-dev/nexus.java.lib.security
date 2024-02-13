package service.filter;

import com.nexus.security.service.filter.SecurityContextInjector;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SecurityContextInjectorTest {

    @Test
    void testInjectContext() {
        String token = "exampleToken";
        SecurityContextInjector securityContextInjector = mock(SecurityContextInjector.class);
        securityContextInjector.injectContext(token);
        verify(securityContextInjector).injectContext(token);
    }
}
