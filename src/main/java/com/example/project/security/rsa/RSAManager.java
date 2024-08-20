package com.example.project.security.rsa;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class RSAManager {

    private RSAKeyPair rsaKeyPair;

    @PostConstruct
    public void init() {
        try {
            RSAGenerator rsaKeyGenerator = new RSAGenerator();
            this.rsaKeyPair = rsaKeyGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating RSA key pair", e);
        }
    }

    public RSAKeyPair getRsaKeyPair() {
        return rsaKeyPair;
    }
}
