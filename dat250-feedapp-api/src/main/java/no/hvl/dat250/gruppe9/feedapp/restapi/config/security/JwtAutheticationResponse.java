package no.hvl.dat250.gruppe9.feedapp.restapi.config.security;

public class JwtAutheticationResponse {

    private String token;
    private String tokenType = "Bearer";
    private String profile;

    public JwtAutheticationResponse(String token, String profile) {
        this.token = token;
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
