package com.devsawe.demo.Model;

public class AuthenticationResponse {
    private final String jwt;
    private final String username;
    private  String userType;


    public AuthenticationResponse(String jwt, String username, String userType) {
        this.jwt = jwt;
        this.username = username;
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getJwt() {
        return jwt;
    }

    public String getUsername() {
        return username;
    }
}
