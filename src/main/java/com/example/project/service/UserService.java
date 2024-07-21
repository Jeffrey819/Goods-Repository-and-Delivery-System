package com.example.project.service;

import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<List<User>> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteByUserId(String userId) {
        userRepository.deleteById(userId);
    }
}
