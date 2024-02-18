package model.dto;

import com.nexus.security.model.dto.TokenProperties;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TokenPropertiesDTOTest {

    @Test
    void AssertEqualsProperties(){
        assertEquals("Igor de campos", this.mockToken().username());
        assertEquals(this.authorities(), this.mockToken().authorities());
    }

    @Test
    void NoAssertEqualsProperties(){
        assertNotEquals("Nome errado", this.mockToken().username());
        assertNotEquals(List.of("Element incorrect", "Element incorrect 2"), this.mockToken().authorities());
    }

    TokenProperties mockToken(){
        return TokenProperties.builder()
                .username("Igor de campos")
                .authorities(List.of("ROLE_USER", "ROLE_ADMIN"))
                .build();
    }

    List<String> authorities() {
        return List.of("ROLE_USER", "ROLE_ADMIN");
    }


}
