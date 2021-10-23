package com.budgetCalculator.services;

import com.budgetCalculator.entity.AppUser;
import com.budgetCalculator.entity.Role;
import com.budgetCalculator.response.UserRequestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface UserServiceInterface {

    AppUser saveUser(AppUser appUser);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName) throws JsonProcessingException;
    AppUser getUser(String username);
    List<AppUser> getUsers();
    UserRequestResponse getUserByUsername(String username);
}
