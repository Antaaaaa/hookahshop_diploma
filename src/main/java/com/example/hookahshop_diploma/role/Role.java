package com.example.hookahshop_diploma.role;

public enum Role {
    ADMIN,USER;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
