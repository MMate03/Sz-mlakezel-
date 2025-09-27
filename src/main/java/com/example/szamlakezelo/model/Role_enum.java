package com.example.szamlakezelo.model;

public enum Role_enum {
    ROLE_USER("Felhasználó"),
    ROLE_ACCOUNTANT("Könyvelő"),
    ROLE_ADMIN("Admin");

    private final String displayName;

    Role_enum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

