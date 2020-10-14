package com.devsawe.demo.Model;

public class AuthenticationRequest {

    private String username;
    private String password;
    private String userType;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public AuthenticationRequest(String jwt) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
