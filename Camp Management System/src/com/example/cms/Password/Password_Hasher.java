package com.example.cms.Password;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
/**
 * Class to provide password encrypting functionality
 */
public class Password_Hasher {
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static String hashPassword(String password, String salt) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // Handle exceptions (e.g., log or throw custom exception)
            e.printStackTrace();
            return null;
        }
    }

    public static String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        System.out.println("This is the salt: " + Base64.getEncoder().encodeToString(salt));
        return Base64.getEncoder().encodeToString(salt);
    }
}
