package com.budgetCalculator.services;

import com.budgetCalculator.entity.Expenditure;

import java.util.List;

public interface ExpenditureServiceInterfaces {
    void expenditureSave(Expenditure expenditure);
    List<Expenditure> getAllExpenditures();

}
