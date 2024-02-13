package model.dto;

import com.nexus.security.model.dto.RoutesDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoutesDTOTest {

    @Test
    void testRoutesDTO() {
        String route = "/api/users";
        HttpMethod method = HttpMethod.GET;
        RoutesDTO routesDTO = new RoutesDTO(route, method);
        assertEquals(route, routesDTO.route());
        assertEquals(method, routesDTO.method());
    }
}
