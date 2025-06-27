package com.gym.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class User {
    private String userId;
    private String password;
    private boolean loginStatus;
    private String userType;

    public User(String userId, String password, String userType) {
        this.userId = userId;
        this.setPassword(password);
        this.loginStatus = false;
        this.userType = userType;
    }

    public String getUserId() { return userId; }
    public String getPassword() { return password; }
    public boolean isLoginStatus() { return loginStatus; }
    public String getUserType() { return userType; }
    public void setUserId(String userId) { this.userId = userId; }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public void setLoginStatus(boolean loginStatus) { this.loginStatus = loginStatus; }
    public void setUserType(String userType) { this.userType = userType; }

    public boolean verifyLogin(String enteredPassword) {
        if (this.password.equals(hashPassword(enteredPassword))) {
            this.loginStatus = true;
            return true;
        }
        return false;
    }
    
    public void logout() { this.loginStatus = false; }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
