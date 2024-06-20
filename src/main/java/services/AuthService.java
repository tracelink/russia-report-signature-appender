package services;

import auth.JWTToken;
import auth.TokenCache;
import model.AuthRequest;
import model.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.HttpClientUtil;
import utilities.JsonParser;
import utilities.StaticMessages;
import utilities.StaticProperties;

public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final TokenCache tokenCache;

    public AuthService() {
        this.tokenCache = new TokenCache();
        this.tokenCache.setJwtToken(authenticate());
    }

    public JWTToken getJwtToken() {
        JWTToken jwtToken = tokenCache.getJwtToken();
        if (jwtToken == null || jwtToken.isExpired()) {
            logger.info("Token is expired or not present, re-authenticating...");
            jwtToken = authenticate();
            tokenCache.setJwtToken(jwtToken);
        }
        return jwtToken;
    }

    private JWTToken authenticate() {
        logger.info("Authenticating user...");
        try {
            AuthRequest authRequest = new AuthRequest(StaticProperties.properties.getProperty("username"), StaticProperties.properties.getProperty("password"));
            String payload = toJson(authRequest); // Convert authRequest to JSON string
            String response = HttpClientUtil.sendPostRequest(StaticProperties.properties.getProperty("baseUri") + StaticProperties.properties.getProperty("tokenPath"), payload, null, null, null, 0);
            logger.info("Token response : " + response);
            AuthResponse authResponse = parseAuthResponse(response); // Parse JSON response to AuthResponse object
            return new JWTToken(authResponse.getToken(), authResponse.getLifeTime(), authResponse.getCertThumbprint());
        } catch (Exception e) {
            if (e.getMessage().contains("401")) {
                logger.error(StaticMessages.AUTH_ERROR_401, e);
                System.exit(1);
            } else {
                logger.error(StaticMessages.AUTH_ERROR_GENERIC, e);
            }
            return null;
        }
    }

    public void refreshToken() {
        logger.info("Refreshing JWT token...");
        this.tokenCache.setJwtToken(authenticate());
    }

    private String toJson(AuthRequest authRequest) {
        // Converts authRequest to JSON string
        return JsonParser.returnJson(authRequest);
    }

    private AuthResponse parseAuthResponse(String response) {
        //Parse Response to Object and assign the values to AuthResponse Object
        return JsonParser.returnObject(response, AuthResponse.class);
    }
}
