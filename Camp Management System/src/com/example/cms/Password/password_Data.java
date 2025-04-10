package com.example.cms.Password;

/**
 * Entity class to provide information for password salting before hashing
 */
public class password_Data {
    private final String password;
    private final String salt;

    public password_Data(String password, String salt) {
        this.password = password;
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }
}

