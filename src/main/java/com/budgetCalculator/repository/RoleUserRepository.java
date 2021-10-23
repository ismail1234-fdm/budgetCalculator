package com.budgetCalculator.repository;

import com.budgetCalculator.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleUserRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
