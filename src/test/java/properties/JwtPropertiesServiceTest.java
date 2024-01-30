package properties;

import com.nexus.security.properties.JwtProperties;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class JwtPropertiesServiceTest {

    @Test
    void AssertEqualsProperties(){
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSignature("test");
        assertEquals("test", jwtProperties.getSignature());
    }

    @Test
    void NotAssertEqualsProperties(){
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSignature("outro");
        assertNotEquals("test", jwtProperties.getSignature());
    }
}
