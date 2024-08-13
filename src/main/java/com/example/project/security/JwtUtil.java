package com.example.project.security;

import com.example.project.entity.User;
import com.example.project.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtUtil {
    @Autowired
    private UserService userService;

    private static final int EXPIRATION_TIME = 60*60*1000;

    private static final String KEY = "aLongKeyThatMatchTheMinimumRequirement";

    public String createToken(String id,String userName){
        JwtBuilder builder = Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));
        builder.claim("userId", id);
        builder.claim("userName", userName);

        Key signinKey = Keys.hmacShaKeyFor(KEY.getBytes());
        builder.signWith(SignatureAlgorithm.HS256, signinKey);
        return builder.compact();
    }

    public int validateToken(String token) {
        try{
            Key signinKey = Keys.hmacShaKeyFor(KEY.getBytes());
            Claims claims = Jwts.parser()
                    .setSigningKey(signinKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String userId = claims.get("userId", String.class);
            String userName = claims.get("userName", String.class);

            Optional<User> user = userService.findByUserId(userId);
            if(user.isPresent()){
                //User found successfully, token validation pass
                return 1;
            }
            else
                return 0;//User not found, which means the token is invalid or modified
        }
        catch(Exception e){
            //The case that token has expired,which is invalid too.
            return 2;
        }
    }

}
