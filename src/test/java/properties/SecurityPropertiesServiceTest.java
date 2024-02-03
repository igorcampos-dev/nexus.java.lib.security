package properties;

import com.nexus.security.properties.SecurityProperties;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SecurityPropertiesServiceTest {

    @Test
    void AssertEqualsProperties(){
        SecurityProperties jwtProperties = new SecurityProperties();
        jwtProperties.setSignature("test");
        assertEquals("test", jwtProperties.getSignature());
    }

    @Test
    void NotAssertEqualsProperties(){
        SecurityProperties jwtProperties = new SecurityProperties();
        jwtProperties.setSignature("outro");
        assertNotEquals("test", jwtProperties.getSignature());
    }
}
