package auth;

import java.time.Duration;

public class JWTToken {
    private String token;
    private long expiresAt;
    private String certThumbprint;

    public String getCertThumbprint() {
        return certThumbprint;
    }

    public void setCertThumbprint(String certThumbprint) {
        this.certThumbprint = certThumbprint;
    }

    // Constructors
    public JWTToken() {}

    public JWTToken(String token, long lifetime, String certThumbprint) {
        this.token = token;
        this.expiresAt = System.currentTimeMillis() + Duration.ofMinutes(lifetime).toMillis();
        this.certThumbprint = certThumbprint;
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
