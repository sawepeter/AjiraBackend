package com.devsawe.demo.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private Long id;
    private String userType;

    public CustomUserDetails(Long id, String username, String password,String userType, Collection<? extends GrantedAuthority> authorities) {
        super(username, password,authorities);
        this.id = id;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public String getUserType() {
        return userType;
    }
}
