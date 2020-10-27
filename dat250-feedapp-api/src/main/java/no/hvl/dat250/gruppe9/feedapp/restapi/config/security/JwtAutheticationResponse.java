package no.hvl.dat250.gruppe9.feedapp.restapi.config.security;

public class JwtAutheticationResponse {

    private String token;
    private String tokenType = "Bearer";

    public JwtAutheticationResponse(String token) {
        this.token = token;
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
}
