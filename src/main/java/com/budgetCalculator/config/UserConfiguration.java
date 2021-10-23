package com.budgetCalculator.config;

import com.budgetCalculator.entity.AppUser;
import com.budgetCalculator.mappers.RequestMapper;
import com.budgetCalculator.repository.AppUserRepository;
import com.budgetCalculator.repository.RoleUserRepository;
import com.budgetCalculator.response.UserRequestResponse;
import com.budgetCalculator.services.UserServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class UserConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    RequestMapper requestMapper;
    @Bean
    UserServiceImp userServiceImp(AppUserRepository userRepistory, RoleUserRepository roleUserRepository) {
        return new UserServiceImp(userRepistory, roleUserRepository, bCryptPasswordEncoder(),requestMapper);
    }

}
