package com.budgetCalculator.repository;

import com.budgetCalculator.entity.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenditureRepository extends JpaRepository<Expenditure, Integer> {

}
