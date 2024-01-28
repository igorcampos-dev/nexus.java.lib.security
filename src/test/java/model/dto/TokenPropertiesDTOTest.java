package model.dto;

import com.nexus.security.model.dto.TokenPropertiesDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TokenPropertiesDTOTest {

    @Test
    void AssertEqualsProperties(){
        TokenPropertiesDTO tokenPropertiesDTO = new TokenPropertiesDTO("user@example.com");

        assertEquals("user@example.com", tokenPropertiesDTO.email());
    }

    @Test
    void NoAssertEqualsProperties(){
        TokenPropertiesDTO tokenPropertiesDTO = new TokenPropertiesDTO("user@example.com");

        assertNotEquals("diferent@example.com", tokenPropertiesDTO.email());
    }


}
