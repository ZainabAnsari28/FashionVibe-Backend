package com.fashionvibe;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FashionvibeApplication {

    public static String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32];
        secureRandom.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public static void main(String[] args) {
        // String secretKey = generateSecretKey();
        // System.out.println("Generated Secret Key: " + secretKey);
        SpringApplication.run(FashionvibeApplication.class, args);
    }

}
