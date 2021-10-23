package com.budgetCalculator.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestForRoleToUserForm {
    private String username;
    private String rolename;
}
