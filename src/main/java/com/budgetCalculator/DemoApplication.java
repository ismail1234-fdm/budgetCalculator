package com.budgetCalculator;

import com.budgetCalculator.entity.AppUser;
import com.budgetCalculator.entity.Role;
import com.budgetCalculator.services.UserServiceImp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserServiceImp userServiceImp) {
        return args -> {
            userServiceImp.saveRole(new Role(null,"ROLE_USER"));
            userServiceImp.saveRole(new Role(null,"ROLE_MANAGER"));
            userServiceImp.saveRole(new Role(null,"ROLE_ADMIN"));
            userServiceImp.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));

            userServiceImp.saveUser(new AppUser(null,"John Travolta","john","12345",new ArrayList<>()));
            userServiceImp.saveUser(new AppUser(null,"Will Smith","will","12345",new ArrayList<>()));
            userServiceImp.saveUser(new AppUser(null,"Jim Carry","jim","12345",new ArrayList<>()));
            userServiceImp.saveUser(new AppUser(null,"Arnold Schwarzengger","arnold","12345",new ArrayList<>()));

            userServiceImp.addRoleToUser("john","ROLE_MANAGER");
            userServiceImp.addRoleToUser("will","ROLE_MANAGER");
            userServiceImp.addRoleToUser("jim","ROLE_ADMIN");
            userServiceImp.addRoleToUser("arnold","ROLE_USER");

        };
    }
}
