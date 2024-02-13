package bean;

import com.nexus.security.bean.SecurityBean;
import com.nexus.security.properties.SecurityProperties;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SecurityBeanTest {

    @Test
    void securityPropertiesBean() {
        SecurityBean securityBean = new SecurityBean();
        SecurityProperties securityProperties = securityBean.securityProperties();
        assertNotNull(securityProperties);
    }

    @Test
    void passwordEncoderBean() {
        PasswordEncoder mockPasswordEncoder = mock(PasswordEncoder.class);
        SecurityBean securityBean = new SecurityBean();
        PasswordEncoder passwordEncoder = securityBean.passwordEncoder();
        assertNotNull(passwordEncoder);
    }
}
