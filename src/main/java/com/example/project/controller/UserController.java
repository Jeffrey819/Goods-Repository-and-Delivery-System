package com.example.project.controller;

import com.example.project.entity.User;
import com.example.project.security.JwtUtil;
import com.example.project.security.rsa.RSAManager;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final RSAManager clientRsaManager;
//    private final RSAManager serverRsaManager;
    @Autowired
    private JwtUtil jwtUtil;

    public UserController(UserService userService,@Qualifier("clientRsaManager") RSAManager clientRsaManager,@Qualifier("serverRsaManager") RSAManager serverRsaManager) {
        this.userService = userService;
        this.clientRsaManager = clientRsaManager;
//        this.serverRsaManager = serverRsaManager;
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


    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestParam("username") String username, @RequestParam("password") String password){
        Map<String,String> info = new Hashtable<>();

        Optional<List<User>> users = userService.findByUsername(username);
        if(users.isPresent()){
            for(User user : users.get()){
                if(user.getPassword().equals(password)){
                    info.put("userId",user.getUserId());
                    String token = jwtUtil.createToken(user.getUserId(),user.getRole());
                    StringBuilder stringBuilder = new StringBuilder();
//                    StringBuilder test_stringBuilder = new StringBuilder();

                    try{
                        stringBuilder.append(clientRsaManager.encrypt(token,clientRsaManager.getRsaKeyPair().getPublicKey()));
//                        test_stringBuilder.append(serverRsaManager.encrypt(token,serverRsaManager.getRsaKeyPair().getPublicKey()));
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    String token_encrypted = stringBuilder.toString();
//                    String token_test = test_stringBuilder.toString();
                    info.put("token",token_encrypted);
//                    info.put("token_test",token_test);
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
            return ResponseEntity.status(HttpStatus.CREATED).body(info);

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
