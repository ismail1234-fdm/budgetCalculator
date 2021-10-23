package com.budgetCalculator.services;

import com.budgetCalculator.entity.AppUser;
import com.budgetCalculator.entity.Role;
import com.budgetCalculator.mappers.RequestMapper;
import com.budgetCalculator.repository.AppUserRepository;
import com.budgetCalculator.repository.RoleUserRepository;
import com.budgetCalculator.response.UserRequestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Transactional
public class UserServiceImp implements UserServiceInterface, UserDetailsService {

    private final AppUserRepository userRepistory;
    private final RoleUserRepository roleUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RequestMapper requestMapper;

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving new user to the database", appUser.getUsername());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return this.userRepistory.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role to the database", role.getName());

        return this.roleUserRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) throws JsonProcessingException {
        log.info("Adding role {} to user []", username, roleName);
        AppUser appUser = this.userRepistory.findByUsername(username);
        Role role = this.roleUserRepository.findByName(roleName);
        appUser.getRoles().add(role);
        this.userRepistory.save(appUser);
//        ObjectMapper mapper = new ObjectMapper();
//        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(appUser));
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {}", username);
        return this.userRepistory.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return this.userRepistory.findAll();
    }

    //Mapper is used

    @Override
    public UserRequestResponse getUserByUsername(String username) {
       return RequestMapper.MAPPER.toUSerRequestResponse(this.userRepistory.getUserbyUsername("john"));
    }

    // Spring uses the Method to fetch the Username from Database
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepistory.findByUsername(username);
        System.out.println("AppUserPassword:" + appUser.getPassword());
        if (appUser == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the Database");
        } else {
            log.error("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            System.out.println("role:" + role);
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        System.out.println("Authorities:" + authorities);
        return new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(), authorities);
    }
}
