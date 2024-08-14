package com.example.project.security;

import com.example.project.entity.User;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService; // Your user service that interacts with the database

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userService.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Convert User to CustomUserDetails
        return new CustomUserDetails(user.getUserId(),user.getUsername(), user.getPassword(), Collections.emptyList(),user.getRole());
    }
}