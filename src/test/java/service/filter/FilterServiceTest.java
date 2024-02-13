package service.filter;

import com.nexus.security.service.filter.FilterService;
import com.nexus.security.service.filter.FilterSupport;
import com.nexus.security.service.filter.SecurityContextInjector;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FilterServiceTest {

    @Mock
    private SecurityContextInjector contextInjector;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private FilterService filterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filterService = new FilterService(contextInjector);
    }

    @Test
    void testDoFilterInternal() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ServletException, IOException {
        FilterSupport filterSupportMock = mock(FilterSupport.class);

        Method recoverTokenMethod = FilterSupport.class.getDeclaredMethod("recoverToken", HttpServletRequest.class);
        recoverTokenMethod.setAccessible(true);
        when(recoverTokenMethod.invoke(filterSupportMock, any(HttpServletRequest.class))).thenReturn(Optional.of("token"));

        try {
            Method doFilterInternalMethod = FilterService.class.getDeclaredMethod("doFilterInternal", HttpServletRequest.class, HttpServletResponse.class, FilterChain.class);
            doFilterInternalMethod.setAccessible(true);
            doFilterInternalMethod.invoke(filterService, request, response, filterChain);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        verify(filterChain, times(1)).doFilter(request, response);
    }
}
