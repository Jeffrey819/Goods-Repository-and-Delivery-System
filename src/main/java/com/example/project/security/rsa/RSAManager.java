package com.example.project.security.rsa;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Component
public class RSAManager {

    private RSAKeyPair rsaKeyPair;

    public void init() {
        try {
            RSAGenerator rsaKeyGenerator = new RSAGenerator();
            this.rsaKeyPair = rsaKeyGenerator.generateKeyPair();
            System.out.println("Public Key: " + this.rsaKeyPair.getPublicKey());
            System.out.println("Private Key: " + this.rsaKeyPair.getPrivateKey());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating RSA key pair", e);
        }
    }

    public RSAKeyPair getRsaKeyPair() {
        return rsaKeyPair;
    }

    public String decrypt(String encryptedData, PrivateKey privateKey) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public String encrypt(String token, PublicKey publicKey) throws Exception {
        byte[] originalTokenBytes = token.getBytes(StandardCharsets.UTF_8);

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedBytes = cipher.doFinal(originalTokenBytes);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}
