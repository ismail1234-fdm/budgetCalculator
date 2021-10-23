package com.budgetCalculator.services;

import com.budgetCalculator.entity.Budget;
import com.budgetCalculator.repository.BudgetRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BudgetServiceImp implements BudgetServiceInterfaces {

    private final BudgetRepository budgetRepository;

    @Override
    public void save(Budget budget) {
        budgetRepository.save(budget);
    }

    @Override
    public Budget findBudgetbyId(Integer id) {
        return budgetRepository.getById(id);
    }

    @Override
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }
}
