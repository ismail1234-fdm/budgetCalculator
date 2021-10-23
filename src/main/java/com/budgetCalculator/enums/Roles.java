package com.budgetCalculator.enums;

import lombok.Getter;

@Getter
public enum Roles  {
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_USER("ROLE_USER");

    private final String role;

    private Roles(String role) {
        this.role = role;
    }
}
