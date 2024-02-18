package service.routes;

import com.nexus.security.model.dto.Routes;
import com.nexus.security.service.filter.FilterService;
import com.nexus.security.service.routes.RoutesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoutesServiceTest {

    @Mock
    private HttpSecurity http;

    @Mock
    private FilterService filterService;

    @Mock
    private RoutesService routesService;

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(http.cors(any())).thenReturn(http);
        when(http.csrf(any())).thenReturn(http);
        when(http.sessionManagement(any())).thenReturn(http);
        when(http.authorizeHttpRequests(any())).thenReturn(http);
    }

    @Test
    void testConfigure() throws Exception {
        List<Routes> routesList = Arrays.asList(new Routes("/public", HttpMethod.GET), new Routes("/public", HttpMethod.POST));
        List<Routes> routesAdmin = Arrays.asList(new Routes("/admin", HttpMethod.GET), new Routes("/admin", HttpMethod.POST));

        routesService = RoutesService.builder()
                .http(http)
                .routesList(routesList)
                .routesAdmin(routesAdmin)
                .filterService(filterService)
                .build();

        SecurityFilterChain securityFilterChain = routesService.configure();
        verify(http, times(1)).build();
        assertEquals(securityFilterChain, securityFilterChain);
        verify(http, times(1)).cors(any());
        verify(http, times(1)).csrf(any());
    }
}
