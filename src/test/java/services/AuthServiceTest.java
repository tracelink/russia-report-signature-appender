package services;

import auth.JWTToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import utilities.HttpClientUtil;
import utilities.StaticProperties;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mockStatic;

class AuthServiceTest {

    @BeforeEach
    public void setUp() throws Exception {
        // Mock StaticProperties
        Properties properties = new Properties();
        properties.setProperty("username", "mockUser");
        properties.setProperty("password", "mockPassword");
        StaticProperties.properties = properties;

    }

    @Test
    void testAuthenticateSuccess() {
        try (MockedStatic<HttpClientUtil> httpClientUtilMockedStatic = mockStatic(HttpClientUtil.class)) {

            // Mock HttpClientUtil behavior
            String mockResponse = "{\"token\":\"mockJwtToken\",\"lifeTime\":3600}";
            httpClientUtilMockedStatic.when(() -> HttpClientUtil.sendPostRequest(any(), any(), any(), any(), any(), anyInt()))
                    .thenReturn(mockResponse);

            // Call authenticate method
            AuthService authService = new AuthService();
            JWTToken jwtToken = authService.getJwtToken();

            // Verify JWTToken is populated correctly
            assertNotNull(jwtToken);
            assertEquals("mockJwtToken", jwtToken.getToken());
            assertFalse(jwtToken.isExpired());
        }
    }
}
