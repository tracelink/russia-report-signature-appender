package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {
    private String token;
    private int lifeTime;
    private String certThumbprint;

    public String getCertThumbprint() {
        return certThumbprint;
    }

    public void setCertThumbprint(String certThumbprint) {
        this.certThumbprint = certThumbprint;
    }

    @JsonCreator
    public AuthResponse(@JsonProperty("token") String token,@JsonProperty("lifeTime") int lifeTime) {
        this.token = token;
        this.lifeTime = lifeTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

}
