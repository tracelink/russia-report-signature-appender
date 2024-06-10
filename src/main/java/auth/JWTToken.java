package auth;

import java.time.Duration;

public class JWTToken {
    private String token;
    private long expiresAt;

    // Constructors
    public JWTToken() {}

    public JWTToken(String token, long lifetime) {
        this.token = token;
        this.expiresAt = System.currentTimeMillis() + Duration.ofMinutes(lifetime).toMillis();
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    // Utility method to check if the token is expired
    public boolean isExpired() {
        return System.currentTimeMillis() > expiresAt;
    }
}
