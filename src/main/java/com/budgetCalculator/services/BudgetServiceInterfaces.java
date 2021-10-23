package com.budgetCalculator.services;

import com.budgetCalculator.entity.Budget;

import java.util.List;

public interface BudgetServiceInterfaces {

    void save(Budget budget);

    Budget findBudgetbyId(Integer id);

    List<Budget> getAllBudgets();
}
