package model.dto;

import com.nexus.security.model.dto.TokenPropertiesDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TokenPropertiesDTOTest {

    @Test
    void AssertEqualsProperties(){
        TokenPropertiesDTO tokenPropertiesDTO = new TokenPropertiesDTO("user@example.com", "ROLE_USER");

        assertEquals("user@example.com", tokenPropertiesDTO.email());
        assertEquals("ROLE_USER", tokenPropertiesDTO.role());

    }

    @Test
    void NoAssertEqualsProperties(){
        TokenPropertiesDTO tokenPropertiesDTO = new TokenPropertiesDTO("user@example.com", "ROLE_USER");

        assertNotEquals("diferent@example.com", tokenPropertiesDTO.email());
        assertNotEquals("ROLE_DIFERENT", tokenPropertiesDTO.role());

    }


}
