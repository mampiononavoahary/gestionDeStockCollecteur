package com.spring.gestiondestock.model.enums;

public enum RoleUser {
    USER,
    ADMIN;
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
