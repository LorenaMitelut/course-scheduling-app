package com.endava.courseschedulingapp.entities;

public enum UserRole {
    ADMIN("ADMIN"),
    TRAINER("TRAINER"),
    CLIENT("CLIENT");

    private String role;

    private UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String toDBRole() {
        return "ROLE_".concat(role);
    }
}
