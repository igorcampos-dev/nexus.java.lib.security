package service.filter;

import com.nexus.security.model.dto.Error;
import com.nexus.security.service.filter.FilterSupport;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilterSupportTest {

    @Test
    void testRecoverToken() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {FilterSupport filterSupport = new FilterSupport();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer token");

        Method method = FilterSupport.class.getDeclaredMethod("recoverToken", HttpServletRequest.class);
        method.setAccessible(true);

        Optional<String> result = (Optional<String>) method.invoke(filterSupport, request);

        assertEquals("token", result.orElse(null));
    }

    @Test
    void testException() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        FilterSupport filterSupport = new FilterSupport();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        IOException exception = new IOException("Test Exception");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        Method method = FilterSupport.class.getDeclaredMethod("exception", Exception.class, HttpServletRequest.class, HttpServletResponse.class);
        method.setAccessible(true);

        method.invoke(filterSupport, exception, request, response);

        verify(response, times(1)).setStatus(HttpStatus.UNAUTHORIZED.value());
        verify(response, times(1)).setContentType(MediaType.APPLICATION_JSON_VALUE);

        String result = stringWriter.toString();
        assertTrue(result.contains("Test Exception"));
    }

    @Test
    void testCreateError() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        FilterSupport filterSupport = new FilterSupport();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/test");

        Method method = FilterSupport.class.getDeclaredMethod("createError", Exception.class, HttpServletRequest.class);
        method.setAccessible(true);

        Error error = (Error) method.invoke(filterSupport, new Exception("Test Error"), request);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), error.status());
        assertEquals("Test Error", error.message());
        assertEquals("/test", error.path());
        assertNotNull(error.timestamp());
    }
}
