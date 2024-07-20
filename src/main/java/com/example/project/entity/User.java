package com.example.project.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "UserID",nullable = false,length = 36)
    private String userId;

    @Column(name = "Username",nullable = false,length = 50)
    private String username;

    @Column(name = "Password",nullable = false,length = 255)
    private String password;

    @Column(name = "Role",nullable = false,length = 50)
    private String role;

    //Constructors
    public User(){}

    public User(String userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    //Getters and Setters
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
