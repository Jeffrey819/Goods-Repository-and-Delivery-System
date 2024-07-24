package com.example.project.controller;

import com.example.project.entity.User;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUserbyUserId(@RequestParam("userId") String userId){
        Map<String,String> info = new Hashtable<>();
        Optional<User> foundUser = userService.findByUserId(userId);
        if(foundUser.isPresent()){
            info.put("username",foundUser.get().getUsername());
            info.put("userId",foundUser.get().getUserId());
            info.put("role",foundUser.get().getRole());
            return ResponseEntity.ok(info);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with userId " + userId + " not found");
        }

    }

//    @PostMapping
//    public ResponseEntity<Map<String,String>> createUser(@RequestBody User newUser){
//        Map<String,String> info = new Hashtable<>();
//        Optional<User> user = userService.findByUserId(newUser.getUserId());
//        if(user.isPresent()){
//            info.put("userId",user.get().getUserId());
//            info.put("message","User already exists");
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(info);
//        }
//        else
//        {
//            userService.save(newUser);
//            info.put("userId",newUser.getUserId());
//            info.put("message","User created successfully");
//            return ResponseEntity.status(HttpStatus.CREATED).body(info);
//        }
//    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestParam("username") String username, @RequestParam("password") String password){
        Map<String,String> info = new Hashtable<>();

        Optional<List<User>> users = userService.findByUsername(username);
        if(users.isPresent()){
            for(User user : users.get()){
                if(user.getPassword().equals(password)){
                    info.put("userId",user.getUserId());
                    info.put("message","Login successfully");
                    return ResponseEntity.ok(info);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username and Password does not match, Please try again!");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user){

        Optional<User> existedUser = userService.findByUserId(user.getUserId());
        if(existedUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with userId " + user.getUserId() + " already exists");
        }
        else
        {
            Map<String,String> info = new Hashtable<>();
            userService.save(user);
            info.put("userId",user.getUserId());
            info.put("message","User registered successfully");

            return ResponseEntity.ok(info);

        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser){
        Optional<User> user = userService.findByUserId(updatedUser.getUserId());
        if(user.isPresent()){
            userService.save(updatedUser);
            Map<String,String> info = new Hashtable<>();
            info.put("userId",updatedUser.getUserId());
            info.put("message","User updated successfully");
            return ResponseEntity.ok(info);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with userId " + updatedUser.getUserId() + " not found");
        }
    }

    @DeleteMapping
    public ResponseEntity<Map<String,String>> deleteUser(@RequestParam("userId") String userId){
        Map<String,String> info = new Hashtable<>();
        userService.deleteByUserId(userId);
        info.put("userId",userId);
        info.put("message","User deleted successfully");
        return ResponseEntity.ok(info);
    }
}
