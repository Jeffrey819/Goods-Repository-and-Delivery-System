package com.example.project.security.rsa;

import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAKeyPair {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public RSAKeyPair(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}