package cput.ac.za.dto;

import cput.ac.za.domain.User;

// Matches the { token, user } shape the Angular AuthService expects
// from every register/login endpoint.
public class AuthResponse {
    private String token;
    private User user;

    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
