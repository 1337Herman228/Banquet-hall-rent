package com.bankets.bankets.controllers;

public class AuthorizedUser {

    private Long userId;
    private boolean isAuthorized = false;
    private String role = "Пользователь";


    public AuthorizedUser(boolean isAuthorized, String role) {
        this.isAuthorized = isAuthorized;
        this.role = role;
    }

    public AuthorizedUser(Long userId, boolean isAuthorized, String role) {
        this.userId = userId;
        this.isAuthorized = isAuthorized;
        this.role = role;
    }

    public AuthorizedUser() {
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
