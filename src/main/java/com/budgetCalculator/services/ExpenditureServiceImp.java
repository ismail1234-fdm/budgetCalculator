package com.budgetCalculator.services;


import com.budgetCalculator.entity.Expenditure;
import com.budgetCalculator.repository.ExpenditureRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ExpenditureServiceImp implements ExpenditureServiceInterfaces{

    ExpenditureRepository expenditureRepository;

    @Override
    public void expenditureSave(Expenditure expenditure) {
            this.expenditureRepository.save(expenditure);
    }

    @Override
    public List<Expenditure> getAllExpenditures() {
        return this.expenditureRepository.findAll();
    }
}
