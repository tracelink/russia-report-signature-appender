package auth;

public class TokenCache {
    private JWTToken jwtToken;

    public JWTToken getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(JWTToken jwtToken) {
        this.jwtToken = jwtToken;
    }
}
