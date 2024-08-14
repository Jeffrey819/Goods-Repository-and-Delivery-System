package com.example.project.security;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final String userId;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String role;

    public CustomUserDetails(String userId,String username, String password, Collection<? extends GrantedAuthority> authorities,String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.role = role;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getUserId() {
        return userId;
    }
}