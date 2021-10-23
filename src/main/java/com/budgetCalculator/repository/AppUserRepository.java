package com.budgetCalculator.repository;

import com.budgetCalculator.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    @Query("select u from AppUser u where u.username = :username")
    AppUser getUserbyUsername(String username);
}
