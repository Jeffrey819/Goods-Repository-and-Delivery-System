package com.example.project.security;

import com.example.project.security.rsa.RSAManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Base64;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    private final RSAManager rsaManager;
    public JwtFilter(@Qualifier("serverRsaManager") RSAManager rsaManager) {
        this.rsaManager = rsaManager;}


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the URL
        String url = request.getRequestURI();
        System.out.println("Request URL: " + url);

        if ("/users/signin".equals(url) || "/users/signup".equals(url)) {
            // Skip token validation for signin and signup requests
            filterChain.doFilter(request, response);
            return;
        }

        // Perform token validation
        String token_encrypted = request.getHeader("Authorization");

        if (token_encrypted == null || token_encrypted.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token not found!");
            response.getWriter().flush();
            return;
        }
        //First decrypt the token
        StringBuilder stringBuilder = new StringBuilder();
        try{
            stringBuilder.append(rsaManager.decrypt(token_encrypted,rsaManager.getRsaKeyPair().getPrivateKey()));
        }
        catch(Exception e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token invalid, decryption failed!");
            response.getWriter().flush();
            return;
        }
        String token = stringBuilder.toString();

        int resultValidation = jwtUtil.validateToken(token);
        if (resultValidation == 0 || resultValidation == 3) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token is invalid!");
            response.getWriter().flush();
            return;
        } else if (resultValidation == 2) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token has expired, please sign in again!");
            response.getWriter().flush();
            return;
        }

        // Token is valid, set authentication context
        String userId = jwtUtil.getUserIdFromToken(token); // Add a method to extract userId from token
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(userId);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

}

