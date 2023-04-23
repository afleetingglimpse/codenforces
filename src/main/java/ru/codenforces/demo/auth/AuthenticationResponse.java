package ru.codenforces.demo.auth;

public class AuthenticationResponse {

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public AuthenticationResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;


}
